package com.trunghoang.generalapp

open class LocalDataSource : IDataSource {
    override fun getAvailableSeats(): List<Int> {
        return arrayListOf(1, 2, 3)
    }
}