package com.trunghoang.generalapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    var id: String,
    var content: String,
    var createdTime: Long
)