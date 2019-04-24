package com.trunghoang.generalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: Repository) : ViewModel() {
    val _availableSeats: MutableLiveData<List<Int>> = MutableLiveData()
    val availableSeats: LiveData<List<Int>> = Transformations.map(_availableSeats) { it }

    fun getAvailableSeats() {
        _availableSeats.value = repository.getAvailableSeats()
    }
}