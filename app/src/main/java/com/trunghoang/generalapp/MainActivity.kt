package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*supportFragmentManager.beginTransaction()
            .add(R.id.constraintMain, BookingFragment.newInstance())
            .commit()*/
        supportFragmentManager.beginTransaction()
            .add(R.id.constraintMain, QuickBookingFragment.newInstance())
            .commit()
    }
}
