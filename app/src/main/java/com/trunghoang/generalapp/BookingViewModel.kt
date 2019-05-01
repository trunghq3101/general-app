package com.trunghoang.generalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class BookingViewModel: ViewModel() {
    private val repository = Repository()
    private val _id = MutableLiveData<Int>()
    private val _availableSeats = Transformations.switchMap(_id) {
        repository.getAvailableSeats(it)
    }
    private val _bookingSeats = MutableLiveData<List<Int>>()
    val availableSeats : LiveData<List<Int>> = Transformations.map(_availableSeats) {
        it
    }
    val bookingSeats : LiveData<List<Int>>
        get() = _bookingSeats

    init {
        getAvailableSeats(1)
    }

    fun bookASeat(seatNumber: Int) {
        _bookingSeats.value = ArrayList<Int>().apply {
            add(seatNumber)
        }
    }

    fun cancelAll() {
        _bookingSeats.value = ArrayList()
    }

    fun getAvailableSeats(id: Int) {
        _id.value = id
    }

    fun confirmBooking() {
        bookingSeats.value?.let {
            repository.sendBookingRequest(it)
        }
    }
}