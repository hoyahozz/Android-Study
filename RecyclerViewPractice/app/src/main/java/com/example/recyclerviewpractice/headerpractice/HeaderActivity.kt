package com.example.recyclerviewpractice.headerpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpractice.viewModel.UserViewModel
import com.example.recyclerviewpractice.databinding.ActivityHeaderBinding

class HeaderActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHeaderBinding.inflate(layoutInflater)
    }
    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = UserSealedAdapter(viewModel)

        binding.rcv.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }

        viewModel.userList.observe(this) {
            Toast.makeText(this, "데이터 변화 발생", Toast.LENGTH_SHORT).show()
            viewModel.fetchTasks(it)
        }

        viewModel.users.observe(this) {
            adapter.submitList(it)
        }


    }
}