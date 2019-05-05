package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val todoRecyclerAdapter: TodoRecyclerAdapter by lazy {
        TodoRecyclerAdapter(ArrayList(), ArrayList(), resources)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = todoRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerAdapter.setNotDoneTodos(getNotDoneTodos())
        todoRecyclerAdapter.setDoneTodos(getDoneTodos())
    }

    fun getNotDoneTodos(): List<Todo> {
        return arrayListOf(
            Todo("Mua quần áo mới"),
            Todo("Mua tay cầm game mới"),
            Todo("Mua xe mới"),
            Todo("Mua nhà mới"),
            Todo("Kiếm gấu mới")
        )
    }

    fun getDoneTodos(): List<Todo> {
        return arrayListOf(
            Todo("Mua áo 1"),
            Todo("Mua áo 2"),
            Todo("Mua áo 3"),
            Todo("Mua áo 4")
        )
    }
}
