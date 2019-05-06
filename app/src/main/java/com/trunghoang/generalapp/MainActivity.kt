package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.journaldev.expandablelistview.CustomExpandableListAdapter
import com.journaldev.expandablelistview.ExpandableListDataPump

class MainActivity : AppCompatActivity() {
    private val todoRecyclerAdapter: TodoRecyclerAdapter by lazy {
        TodoRecyclerAdapter(ArrayList(), ArrayList(), resources)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*recyclerView.adapter = todoRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerAdapter.setNotDoneTodos(getNotDoneTodos())
        todoRecyclerAdapter.setDoneTodos(getDoneTodos())*/
        supportFragmentManager.beginTransaction()
            .add(R.id.constraintMain, ExpandableListFragment.newInstance())
            .commit()
    }

    fun getNotDoneTodos(): List<Todo> {
        val list = ArrayList<Todo>()
        for (i in 1..1000) {
            list.add(Todo("Mua $i"))
        }
        return list
        /*return arrayListOf(
            Todo("Mua quần áo mới"),
            Todo("Mua tay cầm game mới"),
            Todo("Mua xe mới"),
            Todo("Mua nhà mới"),
            Todo("Kiếm gấu mới")
        )*/
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
