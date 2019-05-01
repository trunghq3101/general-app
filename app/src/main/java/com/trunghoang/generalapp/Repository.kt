package com.trunghoang.generalapp

import androidx.lifecycle.MutableLiveData

class Repository {
    fun sendBookingRequest(seats: List<Int>) {}
    fun getAvailableSeats() = arrayListOf(2, 3)
    fun getAvailableSeats(id : Int) = object : MutableLiveData<List<Int>>() {
        override fun onActive() {
            super.onActive()
            this.value = arrayListOf(1, 2, 3, 4)
        }
    }
}