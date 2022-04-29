package com.dongyang.android.samplekoin

import com.dongyang.android.samplekoin.instance.FactoryInstance
import com.dongyang.android.samplekoin.instance.SingleInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Koin 모듈 생성
// 모듈은 관련된 Koin 정의를 그룹화하는데 사용된다.

val singleModule = module {
    single { SingleInstance() }
}

val factoryModule = module {
    factory { FactoryInstance() }
}

// 제공된 객체들 중 get() 부분에 들어가 객체를 알아서 찾아 넣어준다.
val repositoryModule = module {
    single { SampleRepository()}
    single { SampleRepository2(get())}
}

// Repository 모듈이 선언되어 있지 않으면 정상 작동하지 않는다.
val viewModelModule = module {
    viewModel { SampleViewModel(get()) }
    viewModel { SampleViewModel2(get(), get()) }
}