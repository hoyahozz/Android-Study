package com.dongyang.android.mvvm_sample

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.dongyang.android.mvvm_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var userProfile: UserProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchUserProfile()
    }

    private fun fetchUserProfile() {

        userProfile = UserProfile("","","")
        userProfile.name = "홍길동"
        userProfile.phone = "01034567890"
        userProfile.address = "서울"

        updateUI(userProfile)
    }

    private fun updateUI(userProfile: UserProfile) {
        binding.name.text = userProfile.name
        binding.phone.text = userProfile.phone
        binding.address.text = userProfile.address
    }
}