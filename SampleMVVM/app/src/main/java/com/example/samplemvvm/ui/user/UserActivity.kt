package com.example.samplemvvm.ui.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvvm.adapter.RecyclerViewDecoration
import com.example.samplemvvm.adapter.UserAdapter
import com.example.samplemvvm.databinding.ActivityUserBinding
import org.koin.android.ext.android.inject

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel
    private val viewModelFactory: ViewModelProvider.Factory by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        val adapter = UserAdapter()

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@UserActivity)
            this.adapter = adapter
            this.addItemDecoration(RecyclerViewDecoration(10))
        }

        userViewModel.userFavoriteList.observe(this) {
            adapter.submitList(it)
        }
    }
}