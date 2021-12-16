package com.dongyang.android.mvvm_sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.mvvm_sample.databinding.UserlistBinding

class UserRecyclerViewAdapter(
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private var items = listOf<UserProfile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.userId.text = item.id.toString()
        holder.userName.text = item.name
        holder.userPhone.text = item.phone
        holder.userAddress.text = item.address
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemBinding: UserlistBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val userId = itemBinding.itemId
        val userName = itemBinding.itemName
        val userPhone = itemBinding.itemPhone
        val userAddress = itemBinding.itemAddress
    }

    fun observeList(userProfile : List<UserProfile>) {
        items = userProfile
        notifyDataSetChanged()
    }

}

