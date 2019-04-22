package com.trunghoang.generalapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailViewModel : ViewModel() {
    val user = MutableLiveData<User>()

    // Không nên khởi tạo repo như thế này trong thực tế
    private val repository = UserRepository()

    fun loadUser(id: String) {
        // getUser(id) trả về LiveData<User>
        user.value = repository.getUser(id).value
    }
}