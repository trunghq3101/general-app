package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val todoRecyclerAdapter: TodoRecyclerAdapter by lazy {
        TodoRecyclerAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = todoRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerAdapter.setData(getTodos())
    }

    fun getTodos(): List<Todo> {
        return arrayListOf(
            Todo("Mua quần áo mới"),
            Todo("Mua tay cầm game mới"),
            Todo("Mua xe mới"),
            Todo("Mua nhà mới"),
            Todo("Kiếm gấu mới"),
            Todo("Mua giày 1"),
            Todo("Mua giày 2"),
            Todo("Mua giày 3"),
            Todo("Mua giày 4"),
            Todo("Mua giày 5"),
            Todo("Mua giày 6"),
            Todo("Mua giày 1"),
            Todo("Mua giày 2"),
            Todo("Mua giày 3"),
            Todo("Mua giày 4"),
            Todo("Mua giày 5"),
            Todo("Mua giày 6"),
            Todo("Mua giày 1"),
            Todo("Mua giày 2"),
            Todo("Mua giày 3"),
            Todo("Mua giày 4"),
            Todo("Mua giày 5"),
            Todo("Mua giày 6")
        )
    }
}
