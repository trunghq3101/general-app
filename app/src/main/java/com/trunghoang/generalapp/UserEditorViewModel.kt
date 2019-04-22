package com.trunghoang.generalapp

import androidx.lifecycle.*

class UserEditorViewModel : BaseUserEditorViewModel, ViewModel() {
    override var name: MutableLiveData<String> = MutableLiveData()
    override var gender: MutableLiveData<String> = MutableLiveData()
    override var age: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _user = MediatorLiveData<User>().apply {
        addSource(name) {
            value = value?.copy(name = it) ?: User(name = it)
        }
        addSource(gender) {
            value = value?.copy(gender = it) ?: User(gender = it)
        }
        addSource(age) {
            value = value?.copy(age = it) ?: User(age = it)
        }
    }
    var user: LiveData<User> = Transformations.map(_user) { it }

    override fun save() {
    }
}