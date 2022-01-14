package com.example.recyclerviewpractice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice.databinding.ItemUserBinding
import com.example.recyclerviewpractice.model.User
import com.example.recyclerviewpractice.viewModel.UserViewModel

class UserAdapter(
    viewModel: UserViewModel
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(),
    SimpleItemTouchHelperCallback.ItemTouchListener {

    private val TAG = "UserAdapter"
    private var items = arrayListOf<User>()
    private val viewModel = viewModel

    fun setItem(item: List<User>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.phone.text = items[position].phone

        holder.delete.setOnClickListener {
            viewModel.delete(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    // TODO : 라이브데이터와 연결하여 아이템 변경 감지하기!

    // 아이템을 이동하는 메소드
    override fun moveItem(fromPosition: Int, toPosition: Int): Boolean {
        Log.d(TAG, this.items[fromPosition].name)
        Log.d(TAG, this.items[toPosition].name)

        val item = this.items[fromPosition] // fromPosition 의 기존 데이터를 보관하고,
        this.items.removeAt(fromPosition) // fromPosition 데이터를 지우고,
        this.items.add(toPosition, item) // toPosition 으로 데이터를 이동한다.
        notifyItemMoved(fromPosition, toPosition) // 리사이클러뷰에게도 데이터가 이동했다는 사실을 알린다.

        return true
    }

    // 아이템 삭제 메소드
    override fun removeItem(position: Int) {
        if(position != 0) {
            viewModel.delete(this.items[position])
            this.items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class UserViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val phone = binding.phone
        val delete = binding.delete
    }
}