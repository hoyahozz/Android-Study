package com.dongyang.android.samplekoin

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

val repositoryModule = module {
    single { SampleRepository(get())}
    single { SampleRepository2(get())}
}

// Repository 모듈이 선언되어 있지 않으면 정상 작동하지 않는다.
val viewModelModule = module {
    viewModel { SampleViewModel(get()) }
    viewModel { SampleViewModel2(get(), get()) }
}