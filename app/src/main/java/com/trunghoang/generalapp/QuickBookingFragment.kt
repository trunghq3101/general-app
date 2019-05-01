package com.trunghoang.generalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.booking_layout.*
import kotlinx.android.synthetic.main.fragment_quick_booking.*

class QuickBookingFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[BookingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quick_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonBook1.setOnClickListener {
            viewModel.bookASeat(1)
        }
        buttonConfirm.setOnClickListener {
            viewModel.confirmBooking()
        }
        buttonCancelAll.setOnClickListener {
            viewModel.cancelAll()
        }
        viewModel.availableSeats.observe(this, Observer { seats ->
            textAvailableSeats.text = seats.toString()
        })
        viewModel.bookingSeats.observe(this, Observer { seats ->
            textBookingSeats.text = seats.toString()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuickBookingFragment()
    }
}
