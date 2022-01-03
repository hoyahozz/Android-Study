package com.dongyang.android.pcheduler.Adapter

import android.content.Context
import android.graphics.Paint
import android.text.Layout
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.recyclerviewpractice.UserViewModel
import com.example.recyclerviewpractice.databinding.ItemChildUserBinding
import com.example.recyclerviewpractice.headerpractice.UserSealed
import com.example.recyclerviewpractice.headerpractice.UserSealedAdapter

/**
 * @Author : Jeong Ho Kim
 * @Created : 2021-12-02
 * @Description :
 */

class UserChildHolder(
    itemView: View,
    private val UserViewModel: UserViewModel
) : UserSealedAdapter.MyViewHolder(itemView) {
    private val binding by lazy { ItemChildUserBinding.bind(itemView) }

    override fun bind(item: UserSealed) {
        val user = (item as UserSealed.Child).user
        binding.apply {

            val phone = this.phone
            phone.text = user.phone
            val delete = this.delete

            delete.setOnClickListener {
                UserViewModel.delete(user)
            }
        }
    }
}