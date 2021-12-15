package com.dongyang.android.mvvm_sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.mvvm_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var userProfile: UserProfile
    private val viewModel : UserProfileViewModel by viewModels() // 뷰모델 선언

    /* 뷰모델 대신 싱글턴 객체를 사용하면 되지 않나?
     * -> 액티비티가 보여지고 있을 때만 리스너가 호출이 되어야하는데, 싱글턴 객체는 이를 개발자가 직접 코딩해야 한다.
     * -> 뷰모델은 라이프사이클을 알고 있기 때문에, 이 불편함을 해소해준다.
     *
     * 액티비티 종료시 데이터는 초기화된다.
     * -> 액티비티가 완전히 Destroy 되고 안드로이드 시스템 내에서도 사라졌기 때문에
     * -> 자체적으로 관리하고 있던 라이브데이터도 삭제된다.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d("Main", "Callback ON")
                val userProfile = viewModel.livedata.value
                Log.d("Main", it.data?.getStringExtra("phone").toString())
                userProfile?.phone = it.data?.getStringExtra("phone").toString()
                userProfile?.name = it.data?.getStringExtra("name").toString()
                viewModel.livedata.value = userProfile
            }
        }

        binding.edit.setOnClickListener {
            val intent = Intent(this, EditUserProfileActivity::class.java)
            intent.putExtra("phone", userProfile.phone)
            intent.putExtra("name", userProfile.name)
            resultListener.launch(intent)
        }

        viewModel.livedata.observe(this) { // 변경사항 관찰하였을 때 UI 변경
            Log.d("Main", "Observer ON")
            updateUI(it)
        }

        if (viewModel.livedata.value == null) { // livedata 존재하지 않을 시 기본 UI 설정
            Log.d("Main", "livedata null")
            fetchUserProfile()
        }
    }

    private fun fetchUserProfile() {
        userProfile = UserProfile("","","")
        userProfile.name = "홍길동"
        userProfile.phone = "01034567890"
        userProfile.address = "서울"
        viewModel.livedata.value = userProfile
    }

    private fun updateUI(userProfile: UserProfile) {
        binding.name.text = userProfile.name
        binding.phone.text = userProfile.phone
        binding.address.text = userProfile.address
    }
}