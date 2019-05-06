package com.trunghoang.generalapp.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trunghoang.generalapp.MovieRepository
import com.trunghoang.generalapp.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private val repo: MovieRepository by lazy {
        MovieRepository()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = repo.getAFewMovies()
        val adapter = context?.let { ListArrayAdapter(it, data) }
        listView.adapter = adapter
        /*listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                if (scrollState == SCROLL_STATE_IDLE && listView.lastVisiblePosition >= listView.count - 1) {
                    adapter?.addAll(repo.getAFewMovies())
                }
            }
        })*/
        buttonRemove.setOnClickListener {
            val movie = adapter?.getItem(1)
            adapter?.remove(movie)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}
