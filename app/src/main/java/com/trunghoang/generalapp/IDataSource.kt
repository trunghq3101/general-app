package com.trunghoang.generalapp

interface IDataSource {
    fun getAvailableSeats(): List<Int>
}