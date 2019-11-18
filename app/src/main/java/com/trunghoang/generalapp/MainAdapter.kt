package com.trunghoang.generalapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val ctx: Context,
    private val onSelectionChanged: (position: Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val data = ArrayList<String>()
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.d("MainAdapter", "onCreateViewHolder")
        return LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
            .let {
                MainViewHolder(it)
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    fun submitList(newData: List<String>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun updateSelection(newPosition: Int) {
        if (newPosition != selectedPosition) {
            onSelectionChanged(newPosition)
            notifyItemChanged(selectedPosition)
            selectedPosition = newPosition
            notifyItemChanged(selectedPosition)
        }
    }

    fun next() {
        updateSelection(selectedPosition + 1)
    }

    fun prev() {
        if (selectedPosition > 0) updateSelection(selectedPosition - 1)
    }

    inner class MainViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String, position: Int) {
            Log.d("MainViewHolder", "onBind $position")
            itemView.findViewById<TextView>(R.id.textMain).text = item
            itemView.isSelected = position == selectedPosition
            //if (itemView.isSelected) onSelectionChanged(selectedPosition)
        }
    }
}