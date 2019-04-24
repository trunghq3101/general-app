package com.trunghoang.generalapp

import org.junit.Test
import org.junit.Assert.*

class LocalDataSourceTest {
    @Test
    fun whenGetAvailableSeats_thenReturnList() {
        val localDataSource = LocalDataSource()
        assertNotNull(localDataSource.getAvailableSeats())
    }
}