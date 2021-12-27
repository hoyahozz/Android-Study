package com.example.recyclerviewpractice

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SimpleItemTouchHelperCallback(listener: onItemTouchListener) : ItemTouchHelper.Callback()  {

    val listener = listener

    // 드래그와 스와이프를 지원해줄 방향을 여기서 리턴한다.
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.START or ItemTouchHelper.END)

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = listener.moveItem(viewHolder.adapterPosition, target.adapterPosition)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    {
        return listener.removeItem(viewHolder.adapterPosition)
    }

    // 무브, 혹은 스와이프가 일어났을 때 동작해야할 MoveItem, RemoveItem을 인터페이스로 설정
    interface onItemTouchListener {
        fun moveItem(fromPosition : Int, toPosition : Int) : Boolean
        fun removeItem(position : Int)
    }


}