package com.dongyang.android.pcheduler.Adapter

import android.view.View
import com.example.recyclerviewpractice.databinding.ItemParentUserBinding
import com.example.recyclerviewpractice.headerpractice.UserSealed
import com.example.recyclerviewpractice.headerpractice.UserSealedAdapter

/**
 * @Author : Jeong Ho Kim
 * @Created : 2021-12-02
 * @Description :
 */

class UserParentHolder(
    itemView: View
) : UserSealedAdapter.MyViewHolder(itemView) {
    private val binding by lazy { ItemParentUserBinding.bind(itemView) }

    override fun bind(item: UserSealed) {
        val user = (item as UserSealed.Parent).user
        binding.apply {
            this.name.text = user.name
        }
    }
}