package com.trunghoang.generalapp

import androidx.lifecycle.MutableLiveData

interface BaseUserEditorViewModel {
    var name: MutableLiveData<String>
    var gender: MutableLiveData<String>
    var age: MutableLiveData<Int>
    fun save()
}