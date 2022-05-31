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

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.android.advancedcoroutines.util.CacheOnSuccess
import com.example.android.advancedcoroutines.utils.ComparablePair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Repository module for handling data operations.
 *
 * This PlantRepository exposes two UI-observable database queries [plants] and
 * [getPlantsWithGrowZone].
 *
 * To update the plants cache, call [tryUpdateRecentPlantsForGrowZoneCache] or
 * [tryUpdateRecentPlantsCache].
 */
class PlantRepository private constructor(
    private val plantDao: PlantDao,
    private val plantService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    /**
     * Fetch a list of [Plant]s from the database.
     * Returns a LiveData-wrapped List of Plants.
     */
    // val plants = plantDao.getPlants()
    // Livedata & Coroutine, 값을 비동기적으로 계산해야 할 때
    // livedata 빌더를 사용
    val plants: LiveData<List<Plant>> = liveData<List<Plant>> {
        val plantsLiveData = plantDao.getPlants()
        val customSortOrder = plantsListSortOrderCache.getOrAwait()
        emitSource(plantsLiveData.map { plantList ->
            plantList.applySort(customSortOrder)
        })
    }

    /**
     * Fetch a list of [Plant]s from the database that matches a given [GrowZone].
     * Returns a LiveData-wrapped List of Plants.
     */
//    fun getPlantsWithGrowZone(growZone: GrowZone) =
//        plantDao.getPlantsWithGrowZoneNumber(growZone.number)

    // livedata 빌더 사용
    // 캐시데이터가 저장되어 있으므로 매핑 중 네트워크 호출을 실행해도 안전하다.
    fun getPlantsWithGrowZone(growZone: GrowZone) =
        plantDao.getPlantsWithGrowZoneNumber(growZone.number)
            .switchMap { plantList ->
                liveData {
                    val customSortOrder = plantsListSortOrderCache.getOrAwait()
                    emit(plantList.applyMainSafeSort(customSortOrder))
                }
            }

    /**
     * Returns true if we should make a network request.
     */
    private fun shouldUpdatePlantsCache(): Boolean {
        // suspending function, so you can e.g. check the status of the database here
        return true
    }

    /**
     * Update the plants cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsCache() {
        if (shouldUpdatePlantsCache()) fetchRecentPlants()
    }

    /**
     * Update the plants cache for a specific grow zone.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone) {
        if (shouldUpdatePlantsCache()) fetchPlantsForGrowZone(growZoneNumber)
    }

    /**
     * Fetch a new list of plants from the network, and append them to [plantDao]
     */
    private suspend fun fetchRecentPlants() {
        val plants = plantService.allPlants()
        plantDao.insertAll(plants)
    }

    /**
     * Fetch a list of plants for a grow zone from the network, and append them to [plantDao]
     */
    private suspend fun fetchPlantsForGrowZone(growZone: GrowZone) {
        val plants = plantService.plantsByGrowZone(growZone)
        plantDao.insertAll(plants)
    }

    // 캐시 데이터로 저장, 네트워크 오류가 있더라도 데이터를 표시할 수 있다. (현재는 빈 목록 표시)
    private var plantsListSortOrderCache =
        CacheOnSuccess(onErrorFallback = { listOf<String>() }) {
            plantService.customPlantSortOrder()
        }

    // 정렬 함수
    private fun List<Plant>.applySort(customSortOrder: List<String>): List<Plant> {
        return sortedBy { plant ->
            val positionForItem = customSortOrder.indexOf(plant.plantId).let { order ->
                if (order > -1) order else Int.MAX_VALUE
            }
            ComparablePair(positionForItem, plant.name)
        }
    }

    // 람다 전용의 다른 디스패처로 전환 후 시작했던 디스패처에서 다시 시작
    @AnyThread
    suspend fun List<Plant>.applyMainSafeSort(customSortOrder: List<String>) =
        withContext(defaultDispatcher) {
            this@applyMainSafeSort.applySort(customSortOrder)
        }

    /* Coroutine Flow !! */

    // plantsFlow, customSortFlow .. 두 개의 Flow가 있는 상황에서 결합을 하기 위해서는? (Room + Retrofit)
    // combine 연산자를 추가!
    // 두 Flow 모두 자체 코루틴에서 실행되고, 각 Flow에서 새 값이 생성될 때마다 최신 값으로 변환된다.
    // 여기서는 캐시된 네트워크 조회 + 데이터베이스 쿼리와 결합된 모습을 확인할 수 있다.
    // 두 Flow 에서 결과가 나오는 즉시 combine 람다를 호출, 로드된 식물에 로드된 정렬 순서를 적용한다.

    // -------------------------------------------------------------
    // 데이터 세트의 규모가 커질 수록 기본 스레드를 차단할 정도로 속도가 떨어질 수 있음
    // flowOn 을 이용, Flow가 실행되는 스레드를 제어한다.
    // 1. defaultDispatcher(Dispacther.Default)에서 새 코루틴을 실행, flowOn을 호출하기 "전에" Flow를 실행하고 수집한다.
    // 2. "버퍼"를 도입하여 새 코루틴의 결과를 이후 호출로 전송한다.
    // 3. 이 버퍼의 값을 flowOn "이후"의 Flow에 내보낸다. (지금은 ViewModel에 있는 asLivedata)
    // 최근 결과만 저장하기 위해 conflate 를 사용한다.

    val plantsFlow: Flow<List<Plant>>
        get() = plantDao.getPlantsFlow()
            .combine(customSortFlow) { plants, sortOrder ->
                plants.applySort(sortOrder)
            }
            .flowOn(defaultDispatcher)
            .conflate()

    // 비동기 작업 두 개를 결합하더라도 Thread-safety 를 보장한다.
    fun getPlantsWithGrowZoneFlow(growZone: GrowZone): Flow<List<Plant>> {
        return plantDao.getPlantsWithGrowZoneNumberFlow(growZone.number)
            .map { plantList ->
                val sortOrderFromNetwork = plantsListSortOrderCache.getOrAwait()
                val nextValue = plantList.applyMainSafeSort(sortOrderFromNetwork)
                nextValue
            }
    }

    // 데이터 수집 시 getOrAwait, 정렬 순서를 emit 처리하는 Flow
    private val customSortFlow = flow { emit(plantsListSortOrderCache.getOrAwait()) }

    // Create a flow that calls a single function
    // 작동 방식을 보기 위해 onStart 에 delay를 준다.
    // 요렇게 하면 1.5초 뒤에 맞춤 정렬이 적용된다.
//    private val customSortFlow = plantsListSortOrderCache::getOrAwait.asFlow()
//        .onStart {
//            emit(listOf())
//            delay(1500)
//        }



    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao, plantService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao, plantService).also { instance = it }
            }
    }
}
