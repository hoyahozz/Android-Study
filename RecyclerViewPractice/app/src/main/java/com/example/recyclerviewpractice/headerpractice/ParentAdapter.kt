package com.example.recyclerviewpractice.headerpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.User
import com.example.recyclerviewpractice.databinding.ItemParentUserBinding

class ParentAdapter : RecyclerView.Adapter<ParentAdapter.MyViewHolder>() {

    val myList = listOf<User>();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemParentUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = myList.size

    inner class MyViewHolder(binding: ItemParentUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
    }

}