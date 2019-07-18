package com.trunghoang.generalapp.ui.homespot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_home_spot.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeSpotFragment : Fragment() {

    private val viewModel: HomeSpotViewModel by viewModel()
    private val adapter: HomeSpotAdapter = HomeSpotAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_spot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        observeField()
    }

    fun initData() {
        recyclerHomeSpot.adapter = adapter
        recyclerHomeSpot.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_8)))
        ItemTouchHelper(HomeSpotAdapter.SwipeCallback(adapter) { from, to ->
            viewModel.swapItems(from, to)
        }).apply {
            attachToRecyclerView(recyclerHomeSpot)
        }
        viewModel.loadData()
    }

    fun observeField() {
        viewModel.data?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.networkState = it
        })
    }

    companion object {
        fun newInstance() = HomeSpotFragment()
    }
}