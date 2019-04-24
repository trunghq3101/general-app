package com.trunghoang.generalapp

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {
    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Test
    fun whenGetAvailableSeats_thenReturnList() {
        val repository = Repository(localDataSource)
        assertNotNull(repository.getAvailableSeats())
    }
}