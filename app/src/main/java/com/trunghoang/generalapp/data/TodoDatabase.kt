package com.trunghoang.generalapp.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trunghoang.generalapp.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        @JvmStatic
        private var sInstance: TodoDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): TodoDatabase {
            if (sInstance == null) {
                synchronized(TodoDatabase::class) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context,
                            TodoDatabase::class.java,
                            "TodoDatabase"
                        ).build()
                    }
                }
            }
            return sInstance!!
        }
    }
}