package com.trunghoang.generalapp

import androidx.lifecycle.LiveData

interface IUserDataSource {
    fun getUser(id: String): LiveData<User>
}