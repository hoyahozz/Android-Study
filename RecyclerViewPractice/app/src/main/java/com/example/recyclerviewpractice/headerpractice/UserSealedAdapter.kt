package com.example.recyclerviewpractice.headerpractice

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.pcheduler.Adapter.UserChildHolder
import com.dongyang.android.pcheduler.Adapter.UserParentHolder
import com.example.recyclerviewpractice.R
import com.example.recyclerviewpractice.SimpleItemTouchHelperCallback
import com.example.recyclerviewpractice.viewModel.UserViewModel

class UserSealedAdapter(
    private val userViewModel: UserViewModel
) : ListAdapter<UserSealed, UserSealedAdapter.MyViewHolder>(myDiffCallBack),
    ItemTouchHelperCallback.ItemTouchListener {


    override fun getItemViewType(position: Int): Int = getItem(position).layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            UserSealed.Parent.VIEW_TYPE -> UserParentHolder(itemView)
            UserSealed.Child.VIEW_TYPE -> UserChildHolder(itemView, userViewModel)
            else -> throw IllegalArgumentException("Cannot create ViewHolder for view type : $viewType")
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d(TAG, "$position :: ${getItem(position).user.name}")
        Log.d(TAG, "$position :: ${getItem(position).layoutId}")
    }

    abstract class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: UserSealed)
    }

    companion object {
        const val TAG = "UserSealedAdapter"
        val myDiffCallBack = object : DiffUtil.ItemCallback<UserSealed>() {
            override fun areItemsTheSame(oldItem: UserSealed, newItem: UserSealed): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserSealed, newItem: UserSealed): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun moveItem(fromPosition: Int, toPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeItem(position: Int) {
        if(getItem(position).layoutId == R.layout.item_parent_user) return

        if(position >= itemCount) return
        val list = currentList.toMutableList()
        userViewModel.delete(getItem(position).user)
        if (list.removeAt(position) != null) submitList(list)
    }


}