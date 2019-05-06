package com.trunghoang.generalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_double_recycler.*

class DoubleRecyclerFragment : Fragment() {
    private val repo: MovieRepository by lazy {
        MovieRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_double_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = repo.getMovieLists()
        recyclerMain.layoutManager = LinearLayoutManager(context)
        recyclerMain.adapter = MainRecyclerAdapter(
            data,
            RecyclerViewType().apply {
                addViewType(R.layout.item_title, ViewTypePosition(0, 0))
                addViewType(R.layout.item_new_movies, ViewTypePosition(1, 1))
                addViewType(R.layout.item_title, ViewTypePosition(2, 2))
                addViewType(R.layout.item_new_movies, ViewTypePosition(3, 3))
                addViewType(R.layout.item_title, ViewTypePosition(4, 4))
                addViewType(R.layout.item_new_movies, ViewTypePosition(5, 5))
                addViewType(R.layout.item_title, ViewTypePosition(6, 6))
                addViewType(R.layout.item_new_movies, ViewTypePosition(7, 7))
                addViewType(R.layout.item_title, ViewTypePosition(8, 8))
                addViewType(R.layout.item_new_movies, ViewTypePosition(9, 9))
                addViewType(R.layout.item_title, ViewTypePosition(10, 10))
                addViewType(R.layout.item_new_movies, ViewTypePosition(11, 11))
                addViewType(R.layout.item_title, ViewTypePosition(12, 12))
                addViewType(R.layout.item_new_movies, ViewTypePosition(13, 13))
                addViewType(R.layout.item_title, ViewTypePosition(14, 14))
                addViewType(R.layout.item_new_movies, ViewTypePosition(15, 15))
                addViewType(R.layout.item_title, ViewTypePosition(16, 16))
                addViewType(R.layout.item_new_movies, ViewTypePosition(17, 17))
            }
        )
        recyclerMain.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN
                    && rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                    rv.stopScroll()
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = DoubleRecyclerFragment()
    }
}
