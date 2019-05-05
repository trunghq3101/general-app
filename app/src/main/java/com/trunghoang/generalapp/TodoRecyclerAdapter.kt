package com.trunghoang.generalapp

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.android.synthetic.main.item_todo_title.view.*

class TodoRecyclerAdapter(
    private val notDoneTodos: List<Todo>,
    private val doneTodos: List<Todo>,
    private val resource: Resources
) : RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder>() {

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        val vh = TodoViewHolder(v)
        Log.d("CreateViewHolder", vh.toString())
        return vh
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return TodoViewHolder(v)
    }

    override fun getItemCount(): Int {
        val notDoneSize = notDoneTodos.size
        val doneSize = doneTodos.size
        return notDoneSize + titleSize(notDoneSize) + doneSize + titleSize(doneSize)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_todo_title
            in 1..notDoneTodos.size -> R.layout.item_todo
            notDoneTodos.size + 1 -> R.layout.item_todo_title
            else -> R.layout.item_todo
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        when (position) {
            0 -> {
                if (notDoneTodos.isNotEmpty()) holder.bindTitle(resource.getString(R.string.title_not_done_todos))
            }
            in 1..notDoneTodos.size -> {
                holder.bind(notDoneTodos[position - 1])
            }
            notDoneTodos.size + 1 -> {
                if (doneTodos.isNotEmpty()) holder.bindTitle(resource.getString(R.string.title_done_todos))
            }
            in notDoneTodos.size + 2 until itemCount -> {
                holder.bind(doneTodos[position - notDoneTodos.size - 2])
            }
        }
    }

    fun setNotDoneTodos(newDataSet: List<Todo>) {
        (notDoneTodos as ArrayList<Todo>).clear()
        notDoneTodos.addAll(newDataSet)
        notifyDataSetChanged()
    }

    fun setDoneTodos(newDataSet: List<Todo>) {
        (doneTodos as ArrayList<Todo>).clear()
        doneTodos.addAll(newDataSet)
        notifyDataSetChanged()
    }

    private fun titleSize(dataSize: Int): Int {
        return if (dataSize != 0) 1 else 0
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo) {
            itemView.textView.text = todo.content
        }

        fun bindTitle(title: String) {
            itemView.textTitle.text = title
        }
    }
}