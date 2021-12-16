package com.dongyang.android.mvvm_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.mvvm_sample.databinding.ActivityRoomBinding

class RoomActivity : AppCompatActivity() {

    lateinit var binding : ActivityRoomBinding
    lateinit var DB : UserProfileDatabase
    private val viewModel : UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DB = UserProfileDatabase.getInstance(this)!!
        val adapter = UserRecyclerViewAdapter()

        binding.roomRcv.apply {
            this.layoutManager = LinearLayoutManager(this@RoomActivity)
            this.addItemDecoration(RecyclerViewDecoration(1))
            this.adapter = adapter
            this.setHasFixedSize(true)
        }

        val userData = ArrayList<UserProfile>()

        viewModel.userList.observe(this) {
            adapter.observeList(it)
            Toast.makeText(this,"변화를 감지하였습니다.", Toast.LENGTH_LONG).show()
        }

        binding.roomButton.setOnClickListener {
            val name = binding.roomName.text.toString()
            val phone = binding.roomPhone.text.toString()
            val address = binding.roomAddress.text.toString()

            val userProfile = UserProfile(null, name, phone, address)

            viewModel.insertUser(userProfile = userProfile)
        }
    }
}