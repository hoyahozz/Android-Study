package com.dongyang.android.mvvm_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.mvvm_sample.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    // val viewModel : UserViewModel by viewModels() :: Kotlin property delegate
    private lateinit var viewModel : UserViewModel
    private lateinit var twoViewModel : UserViewModel

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        twoViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)


        viewModel.userLiveData.observe(this) {
            Log.d("oneViewModel ::", "ON")
            binding.tvNickname.text = it.nickname
            binding.tvName.text = it.name
            Toast.makeText(this, "변경이 감지되었습니다.", Toast.LENGTH_SHORT).show();
        }

        binding.btnEdit.setOnClickListener {
            val setNickName = binding.edtNickname.text.toString()
            val setName = binding.edtName.text.toString()

            val userData = User(setNickName, setName)
            viewModel.editData(userData)
        }
    }
}