package com.example.recyclerviewpractice.headerpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewpractice.R
import com.example.recyclerviewpractice.databinding.ActivityHeaderBinding

class HeaderActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHeaderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}