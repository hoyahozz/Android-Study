package com.dongyang.android.mvvm_sample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dongyang.android.mvvm_sample.databinding.ActivityEditUserProfileBinding
import com.dongyang.android.mvvm_sample.databinding.ActivityMainBinding

class EditUserProfileActivity : AppCompatActivity() {
    private val binding : ActivityEditUserProfileBinding by lazy {
        ActivityEditUserProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val phone = intent.getStringExtra("phone")
        val name = intent.getStringExtra("name")

        binding.edtPhone.setText(phone)
        binding.edtName.setText(name)

        binding.edtButton.setOnClickListener {
            Log.d("EditUser", "Callback")

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("phone", binding.edtPhone.text.toString())
            intent.putExtra("name", binding.edtName.text.toString())
            setResult(Activity.RESULT_OK, intent) // 값을 보냄
            finish()
        }

    }
}