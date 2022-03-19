package com.example.samplemvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.databinding.ItemUserBinding
import com.example.samplemvvm.util.log

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val favoriteList = mutableListOf<UserEntity>()

    fun submitList(getList : List<UserEntity>) {
        log(getList.toString())
        favoriteList.clear()
        favoriteList.addAll(getList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        log("onCreateViewHolder")
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = favoriteList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        log("size :: ${favoriteList.size.toString()}")
        return favoriteList.size
    }

    inner class UserViewHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : UserEntity) {
            binding.user = item
        }
    }
}