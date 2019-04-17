package com.trunghoang.generalapp


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_adialog.*

class ADialogFragment : DialogFragment() {

    companion object {
        const val TAG = "A Dialog Fragment"
        @JvmStatic
        fun newInstance() = ADialogFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d(TAG, "Attach")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonEndActivity.setOnClickListener {
            activity?.finish()
        }
    }
}
