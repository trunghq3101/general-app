package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.trunghoang.generalapp.recyclerAnimation.RecyclerAnimationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*openFragment(DoubleRecyclerFragment.newInstance())
        openFragment(ListFragment.newInstance())
        openFragment(RecyclerBasicFragment.newInstance())*/
        openFragment(RecyclerAnimationFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.constraintMain, fragment)
            .commit()
    }
}
