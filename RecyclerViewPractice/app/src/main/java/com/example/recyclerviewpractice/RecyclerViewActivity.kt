package com.example.recyclerviewpractice

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerviewpractice.databinding.ActivityRecyclerViewBinding


class RecyclerViewActivity : AppCompatActivity() {

    private val TAG = "RecyclerViewActivity"
    private val binding : ActivityRecyclerViewBinding by lazy {
        ActivityRecyclerViewBinding.inflate(layoutInflater)
    }
    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = UserAdapter(viewModel)
        val listAdapter = UserListAdapter(viewModel)

        /*
    val mIth = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, // 드래그 방향 (위/아래)
            ItemTouchHelper.START or ItemTouchHelper.END // 스와이프 방향 (좌/우 -> 시작/끝)
        ) {

            override fun onMove( // 이동
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition // 시작 위치
                val toPos = target.adapterPosition // 이동할 위치
                return adapter.moveItem(fromPos, toPos)
            }
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) { // 스와이프
                adapter.removeItem(viewHolder.adapterPosition)
            }

            // 드래그 상태인지, 스와이프 상태인지 확인할 수 있게 됨.
            override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) { // 드래그일 때
                    viewHolder!!.itemView.setBackgroundColor(Color.LTGRAY)
                }
            }

            // 드래그 상태 혹은 스와이프 상태가 끝났을 때 원래 ViewHolder로 돌아가기 위해 사용
            // 즉, 상태가 끝났을 때 행동할 메소드
            override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.setBackgroundColor(Color.WHITE)
            }
        })
 */
//        val mIth = ItemTouchHelper(SimpleItemTouchHelperCallback(adapter))
//        mIth.attachToRecyclerView(binding.rvUser) // 적용

        binding.rvUser.apply {
            // this.setHasFixedSize(true) // ListAdapter 에서 사용시 에러 발생
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = listAdapter
            this.addItemDecoration(RecyclerViewDecoration(10))
            this.addItemDecoration(DividerItemDecoration(context, 1))
        }

        viewModel.userList.observe(this) {
            Toast.makeText(this, "데이터 변화 발생", Toast.LENGTH_SHORT).show()
            it?.let {
                listAdapter.submitList(it)
            }
        }

        binding.btnInsert.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val userData = User(null, name, phone)
            viewModel.insert(userData)
        }
    }
}