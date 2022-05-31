/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.advancedcoroutines

import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [Plant]s.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PlantListViewModel internal constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    /* Coroutine Flow !! */
    // 기존의 val plants 와 비교하며 보기
    // asLivedata 확장 함수를 사용하여 Flow를 Livedata로 변환한다. (Livedata 로 변환하지 않고도 Flow 를 UI 레이어로 전달할 수는 있음)
    // val plantsUsingFlow: LiveData<List<Plant>> = plantRepository.plantsFlow.asLiveData()

    // Flow에서 제공된 마지막 값만 보유한다. (MutableStateFlow)
    // 구독을 통해 값에 관한 업데이트를 받을 수도 있으며 전체적으로 Livedata 와 비슷하게 동작한다.
    // 마지막 값을 보유하며 변경사항을 관찰할 수 있다.
    private val growZoneFlow = MutableStateFlow<GrowZone>(NoGrowZone)

    // Livedata의 switchMap 과 정확히 동일한 flatMapLatest 연산자를 사용한다.
    // growZone 이 값을 변경할 때마다 람다가 적용된다.
    val plantsUsingFlow: LiveData<List<Plant>> = growZoneFlow.flatMapLatest { growZone ->
        if (growZone == NoGrowZone) {
            plantRepository.plantsFlow
        } else {
            plantRepository.getPlantsWithGrowZoneFlow(growZone)
        }
    }.asLiveData()

    fun setGrowZoneNumber(num: Int) {
        growZone.value = GrowZone(num)
        growZoneFlow.value = GrowZone(num)

//        launchDataLoad {
//            plantRepository.tryUpdateRecentPlantsForGrowZoneCache(GrowZone(num)) }
    }

    fun clearGrowZoneNumber() {
        growZone.value = NoGrowZone
        growZoneFlow.value = NoGrowZone

//        launchDataLoad {
//            plantRepository.tryUpdateRecentPlantsCache()
//        }
    }


    /**
     * Request a snackbar to display a string.
     *
     * This variable is private because we don't want to expose [MutableLiveData].
     *
     * MutableLiveData allows anyone to set a value, and [PlantListViewModel] is the only
     * class that should be setting values.
     */
    private val _snackbar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _spinner = MutableLiveData<Boolean>(false)
    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * The current growZone selection.
     */
    private val growZone = MutableLiveData<GrowZone>(NoGrowZone)

    /**
     * A list of plants that updates based on the current filter.
     */
    // 필터링에 사용됨
    // switchMap -> 지정된 함수를 입력 LiveData에 적용하고 변환된 결과를 LiveData로 반환한다.
    val plants: LiveData<List<Plant>> = growZone.switchMap { growZone ->
        if (growZone == NoGrowZone) {
            plantRepository.plants
        } else {
            plantRepository.getPlantsWithGrowZone(growZone)
        }
    }

    init {
        clearGrowZoneNumber()

        // map과 달리 매핑 transform 을 호출할 때마다 새 코루틴이 실행된다.
        // 이전 코루틴이 완료되기 전에 growZoneChannel 에서 새 값을 보내면 이전 코루틴을 취소한 후 새 코루틴을 시작한다.
        growZoneFlow.mapLatest { growZone ->
            _spinner.value = true
            if (growZone == NoGrowZone) {
                plantRepository.tryUpdateRecentPlantsCache()
            } else {
                plantRepository.tryUpdateRecentPlantsForGrowZoneCache(growZone)
            }
        }
            .onEach {  _spinner.value = false } // 이전의 Flow에서 값을 내보낼 때마다 호출, 여기서는 처리가 완료된 후 스피너를 초기화하는데 사용
            .catch { throwable ->  _snackbar.value = throwable.message  } // Flow에서 이전에 발생한 예외를 캡처한다. 여기서는 오류 메시지를 표시하도록 지시
            .launchIn(viewModelScope) // viewModel 내에서 Flow 수집
    }

    /**
     * Filter the list to this grow zone.
     *
     * In the starter code version, this will also start a network request. After refactoring,
     * updating the grow zone will automatically kickoff a network request.
     */
//    fun setGrowZoneNumber(num: Int) {
//        growZone.value = GrowZone(num)
//
//        // initial code version, will move during flow rewrite
//        launchDataLoad { plantRepository.tryUpdateRecentPlantsCache() }
//    }

    /**
     * Clear the current filter of this plants list.
     *
     * In the starter code version, this will also start a network request. After refactoring,
     * updating the grow zone will automatically kickoff a network request.
     */
//    fun clearGrowZoneNumber() {
//        growZone.value = NoGrowZone
//
//        // initial code version, will move during flow rewrite
//        launchDataLoad { plantRepository.tryUpdateRecentPlantsCache() }
//    }

    /**
     * Return true iff the current list is filtered.
     */
    fun isFiltered() = growZone.value != NoGrowZone

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a
     * snackbar.
     *
     * By marking [block] as [suspend] this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling
     *              the lambda, the loading spinner will display. After completion or error, the
     *              loading spinner will stop.
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                _snackbar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
