package com.trunghoang.generalapp.recyclerAnimation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.trunghoang.generalapp.R

class CustomItemAnimator : SimpleItemAnimator() {
    private val pendingAdditions: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val pendingChanges: ArrayList<ChangeInfo> = ArrayList()
    private val pendingRemovals: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val pendingMoves: ArrayList<MoveInfo> = ArrayList()

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.let { pendingAdditions.add(it) }
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.let { pendingRemovals.add(it) }
        return true
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        pendingChanges.add(ChangeInfo(oldHolder, newHolder, fromLeft, fromTop, toLeft, toTop))
        return true
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        holder?.itemView?.let {
            val newFromX = fromX + it.translationX
            val newFromY = fromY + it.translationY
            pendingMoves.add(MoveInfo(holder, newFromX.toInt(), newFromY.toInt(), toX, toY))
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun runPendingAnimations() {
        val removalsPending = pendingRemovals.isNotEmpty()
        val movesPending = pendingMoves.isNotEmpty()
        val changesPending = pendingChanges.isNotEmpty()
        val additionsPending = pendingAdditions.isNotEmpty()
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
            // nothing to animate
            return
        }
        for (holder in pendingRemovals) {
            animateRemoveImpl(holder)
        }
        pendingRemovals.clear()
        if (movesPending) {
            val mover = Runnable {
                for (moveInfo in pendingMoves) {
                    animateMoveImpl(moveInfo)
                }
                pendingMoves.clear()
            }
            if (removalsPending) {
                val v = pendingMoves[0].holder?.itemView
                v?.postOnAnimationDelayed(mover, 800)
            } else {
                mover.run()
            }
        }
        if (changesPending) {
            val changer = Runnable {
                for (changeInfo in pendingChanges) {
                    animateChangeImpl(changeInfo)
                }
                pendingChanges.clear()
            }
            if (removalsPending) {
                val v = pendingChanges[0].oldHolder?.itemView
                v?.postOnAnimationDelayed(changer, removeDuration)
            } else {
                changer.run()
            }
        }
        if (additionsPending) {
            val adder = Runnable {
                for (holder in pendingAdditions) {
                    animateAddImpl(holder)
                }
                pendingAdditions.clear()
            }
            if (removalsPending || changesPending || additionsPending) {
                val removeDuration = if (removalsPending) 800 else 0
                val changeDuration = if (changesPending) changeDuration else 0
                val addDuration = if (additionsPending) addDuration else 0
                val totalDelay = removeDuration + Math.max(changeDuration, addDuration)
                val v = pendingAdditions[0].itemView
                v.postOnAnimationDelayed(adder, totalDelay)
            } else {
                adder.run()
            }
        }
    }

    override fun isRunning(): Boolean {
        return (pendingAdditions.isNotEmpty()
                || pendingRemovals.isNotEmpty()
                || pendingChanges.isNotEmpty()
                || pendingMoves.isNotEmpty())
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {

    }

    override fun endAnimations() {

    }

    private fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        AnimatorInflater.loadAnimator(holder.itemView.context, R.animator.add_animator).apply {
            setTarget(holder.itemView)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    dispatchAddFinished(holder)
                }
            })
            start()
        }
    }

    private fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        AnimatorInflater.loadAnimator(holder.itemView.context, R.animator.remove_animator).apply {
            setTarget(holder.itemView)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    dispatchRemoveFinished(holder)
                }
            })
            start()
        }
    }

    private fun animateChangeImpl(changeInfo: ChangeInfo) {

    }

    private fun animateMoveImpl(moveInfo: MoveInfo) {
        val v = moveInfo.holder?.itemView
        val deltaX = moveInfo.toX - moveInfo.fromX
        val deltaY = moveInfo.toY - moveInfo.fromY
        if (deltaX != 0) {
            v?.animate()?.translationX(0F)
        }
        if (deltaY != 0) {
            v?.animate()?.translationY(0F)
        }
    }

    /*override fun recordPreLayoutInformation(
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
    }*/

    companion object {
        class MovieItemHolderInfo(val updateAction: String): ItemHolderInfo()
        class ChangeInfo(
            val oldHolder: RecyclerView.ViewHolder?,
            val newHolder: RecyclerView.ViewHolder?,
            val fromLeft: Int,
            val fromTop: Int,
            val toLeft: Int,
            val toTop: Int)
        class MoveInfo(
            val holder: RecyclerView.ViewHolder?,
            val fromX: Int,
            val fromY: Int,
            val toX: Int,
            val toY: Int)
    }
}