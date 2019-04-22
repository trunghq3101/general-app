package com.trunghoang.generalapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment : Fragment() {
    private val userDetailViewModel by lazy {
        ViewModelProviders.of(this)[UserDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userDetailViewModel.user.observe(this, Observer {
            textName.text = it.name
            textAge.text = it.age.toString()
            textStatus.text = it.friendStatus
        })
        userDetailViewModel.loadUser(FAKE_ID)
        userDetailViewModel.user.value =
            userDetailViewModel.user.value?.copy(friendStatus = "Chờ xác nhận")
    }

    companion object {
        const val FAKE_ID = "fakeID"
        @JvmStatic
        fun newInstance() = UserDetailFragment()
    }
}
