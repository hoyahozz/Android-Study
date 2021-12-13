package com.dongyang.android.mvvm_sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.mvvm_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater) // Binding 활성화
    }

    lateinit var userProfile: UserProfile // 지연초기화
    var livedata = MutableLiveData<UserProfile>() // LiveData 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchUserProfile() // 초기 프로필 설정

        val resultListener = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { // 액티비티로부터 오는 값을 받아옴
            if (it.resultCode == Activity.RESULT_OK) {
                val userProfile = livedata.value
                userProfile?.phone = it.data?.getStringExtra("phone").toString()
                userProfile?.name = it.data?.getStringExtra("name").toString()
                livedata.value = userProfile // 밸류값 재설정 (옵저버 가동)
            }
        }

        binding.edit.setOnClickListener {
            val intent = Intent(this, EditUserProfileActivity::class.java)
            intent.putExtra("phone", userProfile.phone)
            intent.putExtra("name", userProfile.name)
            resultListener.launch(intent)
        }

        livedata.observe(this) { // 변경사항 관찰하였을 때 UI 변경
            Log.d("Main", "Observer ON")
            updateUI(it)
        }

        if (livedata.value == null) { // livedata 존재하지 않을 시 기본 UI 설정
            fetchUserProfile()
        }


    }

    private fun fetchUserProfile() {

        userProfile = UserProfile("","","")
        userProfile.name = "홍길동"
        userProfile.phone = "01034567890"
        userProfile.address = "서울"
        livedata.value = userProfile
    }

    private fun updateUI(userProfile: UserProfile) {
        binding.name.text = userProfile.name
        binding.phone.text = userProfile.phone
        binding.address.text = userProfile.address
    }
}