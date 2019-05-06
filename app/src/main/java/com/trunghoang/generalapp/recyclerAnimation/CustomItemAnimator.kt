package com.trunghoang.generalapp.recyclerAnimation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.trunghoang.generalapp.R

class CustomItemAnimator : SimpleItemAnimator() {
    override fun animateChange(oldHolder: RecyclerView.ViewHolder?,
                               newHolder: RecyclerView.ViewHolder?,
                               fromLeft: Int,
                               fromTop: Int,
                               toLeft: Int,
                               toTop: Int): Boolean {
        if (preInfo is MovieItemHolderInfo) {
            when (preInfo.updateAction) {
                MovieAnimationAdapter.ACTION_LIKE_IMAGE -> (newHolder as MovieAnimationAdapter.MovieViewHolder).animateLike(this)
            }
            return false
        }
    }

    override fun runPendingAnimations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun animateMove(holder: RecyclerView.ViewHolder?,
                             fromX: Int,
                             fromY: Int,
                             toX: Int,
                             toY: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRunning(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endAnimations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>): Boolean {
        return true
    }

    override fun recordPreLayoutInformation(
        state: RecyclerView.State,
        viewHolder: RecyclerView.ViewHolder,
        changeFlags: Int,
        payloads: MutableList<Any>
    ): ItemHolderInfo {
        if (changeFlags == RecyclerView.ItemAnimator.FLAG_CHANGED) {
            for (payload in payloads) {
                if (payload is String) {
                    return MovieItemHolderInfo(payload)
                }
            }
        }
        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        AnimatorInflater.loadAnimator(holder?.itemView?.context, R.animator.add_animator).apply {
            setTarget(holder?.itemView)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    holder?.let { dispatchAnimationFinished(it) }
                }
            })
            start()
        }
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        AnimatorInflater.loadAnimator(holder?.itemView?.context, R.animator.remove_animator).apply {
            setTarget(holder?.itemView)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    holder?.let {
                        dispatchAnimationFinished(it)
                    }
                }
            })
            start()
        }
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preInfo: ItemHolderInfo,
        postInfo: ItemHolderInfo
    ): Boolean {
        if (preInfo is MovieItemHolderInfo) {
            when (preInfo.updateAction) {
                MovieAnimationAdapter.ACTION_LIKE_IMAGE -> (newHolder as MovieAnimationAdapter.MovieViewHolder).animateLike(this)
            }
            return false
        }
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    companion object {
        class MovieItemHolderInfo(val updateAction: String): ItemHolderInfo()
    }
}