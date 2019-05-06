package com.trunghoang.generalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.journaldev.expandablelistview.CustomExpandableListAdapter
import com.journaldev.expandablelistview.ExpandableListDataPump
import kotlinx.android.synthetic.main.fragment_expandable_list.*

class ExpandableListFragment : Fragment() {
    var expandableListView: ExpandableListView? = null
    var expandableListAdapter: ExpandableListAdapter? = null
    var expandableListDetail: HashMap<String, List<String>> = ExpandableListDataPump.data
    var expandableListTitle: List<String> = ArrayList<String>(expandableListDetail.keys)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expandable_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expandableListView = expandableList
        expandableListAdapter =
            CustomExpandableListAdapter(requireContext(), expandableListTitle, expandableListDetail)
        expandableListView?.setAdapter(expandableListAdapter)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExpandableListFragment()
    }
}
