package com.trunghoang.generalapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookingViewModel: ViewModel() {
    private val repository = Repository()
    val availableSeats = MutableLiveData<List<Int>>().apply {
        value = arrayListOf(1, 2, 3)
    }
    val bookingSeats = MutableLiveData<List<Int>>()

    fun bookASeat(seatNumber: Int) {
        bookingSeats.value = ArrayList<Int>().apply {
            bookingSeats.value?.let {
                addAll(it)
            }
            add(seatNumber)
        }
    }

    fun clearAllSeats() {
        bookingSeats.value = ArrayList()
        availableSeats.value = repository.getAvailableSeats()
    }

    fun confirmBooking() {
        bookingSeats.value?.let {
            repository.sendBookingRequest(it)
        }
    }
}