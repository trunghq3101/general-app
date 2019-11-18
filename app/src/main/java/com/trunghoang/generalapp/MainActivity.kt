package com.trunghoang.generalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this) {
            if ((rvMain.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition() < it
            ) {
                (rvMain.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    it - 2,
                    0
                )
            } else if ((rvMain.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition() > it) {
                (rvMain.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    it,
                    0
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val data = ArrayList<String>().apply {
            for (i in 0 until 1000) {
                add("This is a text $i")
            }
        }
        rvMain.adapter = mainAdapter
        rvMain.itemAnimator = DefaultItemAnimator().apply {
            supportsChangeAnimations = false
        }
        mainAdapter.submitList(data)

        btnNext.setOnClickListener {
            mainAdapter.next()
        }

        btnPrev.setOnClickListener {
            mainAdapter.prev()
        }
    }
}
