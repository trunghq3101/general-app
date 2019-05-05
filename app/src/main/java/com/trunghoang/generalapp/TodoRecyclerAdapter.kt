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
    private val displayNotDoneTodos: List<Todo> = ArrayList()
    private val displayDoneTodos: List<Todo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return TodoViewHolder(v)
    }

    override fun getItemCount(): Int {
        val notDoneSize = displayNotDoneTodos.size
        val doneSize = displayDoneTodos.size
        return notDoneSize + titleSize(notDoneTodos.size) + doneSize + titleSize(doneTodos.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_todo_title
            in 1..displayNotDoneTodos.size -> R.layout.item_todo
            displayNotDoneTodos.size + 1 -> R.layout.item_todo_title
            else -> R.layout.item_todo
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        when (position) {
            0 -> {
                if (notDoneTodos.isNotEmpty()) holder.bindNotDoneTitle(
                    resource.getString(R.string.title_not_done_todos, notDoneTodos.size)
                ) {
                    toggleNotDoneTodos()
                }
            }
            in 1..displayNotDoneTodos.size -> {
                holder.bind(notDoneTodos[position - 1])
            }
            displayNotDoneTodos.size + 1 -> {
                if (doneTodos.isNotEmpty()) holder.bindDoneTitle(
                    resource.getString(R.string.title_done_todos, doneTodos.size)
                ) {
                    toggleDoneTodos()
                }
            }
            in displayNotDoneTodos.size + 2 until itemCount -> {
                holder.bind(doneTodos[position - displayNotDoneTodos.size - 2])
            }
        }
    }

    fun setNotDoneTodos(newDataSet: List<Todo>) {
        (notDoneTodos as ArrayList<Todo>).clear()
        notDoneTodos.addAll(newDataSet)
        if (displayNotDoneTodos.isNotEmpty()) showNotDoneTodos()
    }

    fun setDoneTodos(newDataSet: List<Todo>) {
        (doneTodos as ArrayList<Todo>).clear()
        doneTodos.addAll(newDataSet)
        if (displayDoneTodos.isNotEmpty()) showDoneTodos()
    }

    fun showNotDoneTodos() {
        (displayNotDoneTodos as ArrayList<Todo>).clear()
        displayNotDoneTodos.addAll(notDoneTodos)
        notifyDataSetChanged()
    }

    fun showDoneTodos() {
        (displayDoneTodos as ArrayList<Todo>).clear()
        displayDoneTodos.addAll(doneTodos)
        notifyDataSetChanged()
    }

    fun hideNotDoneTodos() {
        (displayNotDoneTodos as ArrayList<Todo>).clear()
        notifyDataSetChanged()
    }

    fun hideDoneTodos() {
        (displayDoneTodos as ArrayList<Todo>).clear()
        notifyDataSetChanged()
    }

    fun toggleNotDoneTodos() {
        if (displayNotDoneTodos.isEmpty()) showNotDoneTodos() else hideNotDoneTodos()
    }

    fun toggleDoneTodos() {
        if (displayDoneTodos.isEmpty()) showDoneTodos() else hideDoneTodos()
    }

    private fun titleSize(dataSize: Int): Int {
        return if (dataSize != 0) 1 else 0
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo) {
            itemView.textView.text = todo.content
        }

        fun bindNotDoneTitle(title: String, toggle: () -> Unit) {
            itemView.textTitle.text = title
            itemView.constraintTodoTitle.setOnClickListener {
                toggle()
            }
        }

        fun bindDoneTitle(title: String, toggle: () -> Unit) {
            itemView.textTitle.text = title
            itemView.constraintTodoTitle.setOnClickListener {
                toggle()
            }
        }
    }
}