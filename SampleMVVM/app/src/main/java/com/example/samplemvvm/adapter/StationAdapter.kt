package com.example.samplemvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvm.data.remote.entity.Row
import com.example.samplemvvm.databinding.ItemListBinding
import com.example.samplemvvm.ui.main.MainClickListener

class StationAdapter : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private val stationList = mutableListOf<Row>()
    private var itemLongClickListener : MainClickListener? = null

    fun submitList(getList : List<Row>) {
        stationList.clear()
        stationList.addAll(getList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val item = stationList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = stationList.size

    fun setItemLongClickListener(listener: MainClickListener) {
        itemLongClickListener = listener
    }

    inner class StationViewHolder(private val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Row) {
            binding.station = item
            binding.itemLongClickListener = itemLongClickListener
        }
    }
}