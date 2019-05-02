package com.trunghoang.generalapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trunghoang.generalapp.data.TodoDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = initTodoDatabase(this)
    }

    fun initTodoDatabase(context: Context) : TodoDatabase? = TodoDatabase.getInstance(context)
}
