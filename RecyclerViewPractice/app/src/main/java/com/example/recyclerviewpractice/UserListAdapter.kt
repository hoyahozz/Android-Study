package com.example.recyclerviewpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.databinding.ItemUserBinding
import com.example.recyclerviewpractice.model.User
import com.example.recyclerviewpractice.viewModel.UserViewModel


class UserListAdapter(
    private val viewModel: UserViewModel
) : ListAdapter<User, UserListAdapter.UserViewHolder>(myDiffCallback) {

    inner class UserViewHolder(private val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            binding.name.text = user.name
            binding.phone.text = user.phone

            binding.delete.setOnClickListener {
                viewModel.delete(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder (
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val myDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            // 이전 어댑터와 바뀌는 어댑터의 아이템이 동일한 지 확인을 합니다.
            // 각 아이템의 고유 ID값(DB에서 key 같은 것을 받아온다면 그걸 활용)이 있을 것입니다. 이를 활용하여 비교를 해주시면 됩니다.

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            // 이전 어댑터와 바뀌는 어댑터의 아이템 내 내용을 비교합니다.
            // areItemsTheSame 에서 true가 나올 경우 추가적으로 비교하기 위해서 사용하는 함수입니다.
        }
    }

}