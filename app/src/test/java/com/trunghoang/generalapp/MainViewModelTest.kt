package com.trunghoang.generalapp

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private lateinit var repository: Repository

    @Test
    fun whenGetAvailableSeats_thenReturnList() {
        val viewModel = MainViewModel(repository)
        viewModel.getAvailableSeats()
        assertTrue(viewModel.availableSeats.value!!.isNotEmpty())
    }
}