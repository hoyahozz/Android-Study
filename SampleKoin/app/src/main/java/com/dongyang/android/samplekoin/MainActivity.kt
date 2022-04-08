package com.dongyang.android.samplekoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singleInstance : SingleInstance by inject()
        val singleInstance2 : SingleInstance by inject()
        val singleInstance3 : SingleInstance by inject()
        val factoryInstance : FactoryInstance by inject()
        val factoryInstance2 : FactoryInstance by inject()
        val factoryInstance3 : FactoryInstance by inject()

        val repository : SampleRepository by inject()

        val viewModel : SampleViewModel by viewModel()
        val viewModel2 : SampleViewModel2 by viewModel()



        // 등록된 객체의 인스턴스 요청
        // 싱글톤 객체의 경우 카운트가 늘어나지 않는 모습 확인
        log(singleInstance.hello())
        log(singleInstance2.hello())
        log(singleInstance3.hello())

        // 팩토리 객체는 생성할 때마다 카운트가 늘어나는 모습 확인
        // 의존성을 요청할 때마다 새로운 객체 생성
        log(factoryInstance.hello())
        log(factoryInstance2.hello())
        log(factoryInstance3.hello())


        log(repository.hello())

        log(viewModel.hello())
        log(viewModel2.hello2())

    }
}