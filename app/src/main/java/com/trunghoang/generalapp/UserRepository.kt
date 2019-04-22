package com.trunghoang.generalapp

import androidx.lifecycle.MutableLiveData

class UserRepository : IUserDataSource {
    override fun getUser(id: String) = MutableLiveData<User>().apply {
        value = User("Hoang Quoc Trung", 25)
    }
}