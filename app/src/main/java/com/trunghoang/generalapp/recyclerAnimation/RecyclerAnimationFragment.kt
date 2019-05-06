package com.trunghoang.generalapp.recyclerAnimation


import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.generalapp.MovieRepository

import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.model.Movie
import kotlinx.android.synthetic.main.fragment_recycler_animation.*

class RecyclerAnimationFragment : Fragment() {
    private val repo = MovieRepository()
    private val adapter: MovieAnimationAdapter = MovieAnimationAdapter(repo.getMovies())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                e?.let {
                    val child = recyclerAnimation.findChildViewUnder(e.x, e.y)
                    val position = child?.let { recyclerAnimation.getChildAdapterPosition(it) }
                    position?.let { adapter.removeItem(it) }
                    return true
                }
                return false
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                e?.let {
                    val child = recyclerAnimation.findChildViewUnder(e.x, e.y)
                    val position = child?.let { recyclerAnimation.getChildAdapterPosition(it) }
                    position?.let { adapter.updateLike(position) }
                    return true
                }
                return true
            }
        })
        val linearLayout = object : LinearLayoutManager(context) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return checkPredictive.isChecked
            }
        }
        val gridLayout = object : GridLayoutManager(context, 3) {
            override fun supportsPredictiveItemAnimations(): Boolean {
                return checkPredictive.isChecked
            }
        }
        radioLayout.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioLinear -> recyclerAnimation.layoutManager = linearLayout
                R.id.radioGrid -> recyclerAnimation.layoutManager = gridLayout
            }
        }
        radioLayout.check(R.id.radioGrid)
        recyclerAnimation.adapter = adapter
        recyclerAnimation.itemAnimator = CustomItemAnimator()
        recyclerAnimation.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
        buttonReload.setOnClickListener {
            reloadData()
        }
        buttonAdd.setOnClickListener {
            adapter.addItem(repo.getMovie())
        }
    }

    private fun reloadData() {
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_item_fall_down)
        with(recyclerAnimation) {
            layoutAnimation = controller
            this@RecyclerAnimationFragment.adapter.setData(repo.getMovies())
            scheduleLayoutAnimation()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerAnimationFragment()
    }
}
