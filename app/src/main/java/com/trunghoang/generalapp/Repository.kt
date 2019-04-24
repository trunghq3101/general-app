package com.trunghoang.generalapp

open class Repository(private val local: IDataSource) : IDataSource {
    override fun getAvailableSeats(): List<Int> {
        return local.getAvailableSeats()
    }
}