package com.example.recyclerviewpractice.headerpractice

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.pcheduler.Adapter.UserParentHolder
import com.example.recyclerviewpractice.headerpractice.UserSealedAdapter

class ItemTouchHelperCallback(private val listener: UserSealedAdapter) :
    ItemTouchHelper.Callback() {

    // 드래그와 스와이프를 지원해줄 방향을 여기서 리턴한다.
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is UserParentHolder) {
            makeMovementFlags(
                0,
                0
            )
        } else {
            makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.START or ItemTouchHelper.END
            )
        }

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = listener.moveItem(viewHolder.adapterPosition, target.adapterPosition)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        return listener.removeItem(viewHolder.adapterPosition)
    }

    // 무브, 혹은 스와이프가 일어났을 때 동작해야할 MoveItem, RemoveItem을 인터페이스로 설정
    interface ItemTouchListener {
        fun moveItem(fromPosition: Int, toPosition: Int): Boolean
        fun removeItem(position: Int)
    }


}