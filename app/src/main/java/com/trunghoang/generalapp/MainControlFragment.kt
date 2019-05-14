package com.trunghoang.generalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trunghoang.generalapp.list.ListFragment
import com.trunghoang.generalapp.recyclerAnimation.RecyclerAnimationFragment
import com.trunghoang.generalapp.recyclerBasic.RecyclerBasicFragment
import kotlinx.android.synthetic.main.fragment_main_control.*

class MainControlFragment : Fragment(), View.OnClickListener {
    private val fragmentCallback: FragmentCallback? by lazy {
        if (context is FragmentCallback) {
            context as FragmentCallback
        } else {
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonListFragment.setOnClickListener(this)
        buttonRecyclerBasicFragment.setOnClickListener(this)
        buttonDoubleRecyclerFragment.setOnClickListener(this)
        buttonRecyclerAnimationFragment.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonListFragment -> openFragment(ListFragment.newInstance())
            R.id.buttonRecyclerBasicFragment -> openFragment(RecyclerBasicFragment.newInstance())
            R.id.buttonDoubleRecyclerFragment -> openFragment(DoubleRecyclerFragment.newInstance())
            R.id.buttonRecyclerAnimationFragment -> openFragment(RecyclerAnimationFragment.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        fragmentCallback?.openFragment(fragment)
    }

    interface FragmentCallback {
        fun openFragment(fragment: Fragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainControlFragment()
    }
}
