package com.trunghoang.generalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class BookingViewModel : ViewModel() {
    private val repository = Repository()
    /*val availableSeats = MutableLiveData<List<Int>>().apply {
        value = arrayListOf(1, 2, 3)
    }
    val bookingSeats = MutableLiveData<List<Int>>()*/
    private val _availableSeats = MutableLiveData<List<Int>>().apply {
        value = arrayListOf(1, 2, 3)
    }
    val availableSeats: LiveData<List<Int>> = Transformations.map(_availableSeats) { it }
    private val _bookingSeats = MutableLiveData<List<Int>>()
    val bookingSeats: LiveData<List<Int>> = Transformations.map(_bookingSeats) { it }

    fun bookASeat(seatNumber: Int) {
        _bookingSeats.value = ArrayList<Int>().apply {
            _bookingSeats.value?.let {
                addAll(it)
            }
            add(seatNumber)
        }
    }

    fun clearAllSeats() {
        _bookingSeats.value = ArrayList()
        _availableSeats.value = repository.getAvailableSeats()
    }

    fun confirmBooking() {
        _bookingSeats.value?.let {
            repository.sendBookingRequest(it)
        }
    }
}