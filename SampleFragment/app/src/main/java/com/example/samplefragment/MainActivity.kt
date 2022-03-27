package com.example.samplefragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.samplefragment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FirstFragment())
            .commit()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text.toString()) {
                    "first" -> supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FirstFragment())
                        .commit()
                    "second" -> supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SecondFragment())
                        .addToBackStack(null)
                        .commit()
                    else -> supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ThirdFragment())
                        .commit()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // NOT IMPLEMENTS
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // NOT IMPLEMENTS
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }
}