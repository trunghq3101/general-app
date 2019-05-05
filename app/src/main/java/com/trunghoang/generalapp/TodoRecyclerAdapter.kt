package com.trunghoang.generalapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoRecyclerAdapter(
    private val dataSet: List<Todo>
) : RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        val vh = TodoViewHolder(v)
        Log.d("CreateViewHolder", vh.toString())
        return vh
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun setData(newDataSet: List<Todo>) {
        (dataSet as ArrayList<Todo>).clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo) {
            itemView.textView.text = todo.content
        }
    }
}