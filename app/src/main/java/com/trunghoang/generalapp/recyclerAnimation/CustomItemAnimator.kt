package com.trunghoang.generalapp.recyclerAnimation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.trunghoang.generalapp.R

class CustomItemAnimator : SimpleItemAnimator() {
    private val pendingAdditions: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val pendingChanges: ArrayList<ChangeInfo> = ArrayList()
    private val pendingRemovals: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val pendingMoves: ArrayList<MoveInfo> = ArrayList()
    private val addAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val changeAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val removeAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val moveAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val addList: ArrayList<ArrayList<RecyclerView.ViewHolder>> = ArrayList()
    private val changeList: ArrayList<ArrayList<ChangeInfo>> = ArrayList()
    private val moveList: ArrayList<ArrayList<MoveInfo>> = ArrayList()

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
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
        holder?.let {
            holder.itemView.alpha = 0F
            pendingAdditions.add(it)
        }
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.let {
            pendingRemovals.add(it)
        }
        return true
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
            return true
        }
        return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        if (oldHolder === newHolder) {
            // Don't know how to run change animations when the same view holder is re-used.
            // run a move animation to handle position changes.
            return animateMove(oldHolder, fromLeft, fromTop, toLeft, toTop)
        }
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
            val deltaX = toX - fromX
            val deltaY = toY - fromY
            if (deltaX != 0) {
                it.translationX = (-deltaX).toFloat()
            }
            if (deltaY != 0) {
                it.translationY = (-deltaY).toFloat()
            }
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
            val moves = ArrayList<MoveInfo>()
            moves.addAll(pendingMoves)
            moveList.add(moves)
            pendingMoves.clear()
            val mover = Runnable {
                for (moveInfo in moves) {
                    animateMoveImpl(moveInfo)
                }
                moves.clear()
                moveList.remove(moves)
            }
            if (removalsPending) {
                val v = moves[0].holder?.itemView
                v?.postOnAnimationDelayed(mover, 800)
            } else {
                mover.run()
            }
        }
        if (changesPending) {
            val changes = ArrayList<ChangeInfo>()
            changes.addAll(pendingChanges)
            changeList.add(changes)
            pendingChanges.clear()
            val changer = Runnable {
                for (changeInfo in changes) {
                    animateChangeImpl(changeInfo)
                }
                changes.clear()
                changeList.remove(changes)
            }
            if (removalsPending) {
                val v = changes[0].oldHolder?.itemView
                v?.postOnAnimationDelayed(changer, 800)
            } else {
                changer.run()
            }
        }
        if (additionsPending) {
            val adds = ArrayList<RecyclerView.ViewHolder>()
            adds.addAll(pendingAdditions)
            addList.add(adds)
            pendingAdditions.clear()
            val adder = Runnable {
                for (holder in adds) {
                    animateAddImpl(holder)
                }
                adds.clear()
                addList.remove(adds)
            }
            if (removalsPending || changesPending || additionsPending) {
                val removeDuration = if (removalsPending) 800 else 0
                val changeDuration = if (changesPending) 800 else 0
                val addDuration = if (additionsPending) 800 else 0
                val totalDelay = removeDuration + Math.max(changeDuration, addDuration)
                val v = adds[0].itemView
                v.postOnAnimationDelayed(adder, totalDelay.toLong())
            } else {
                adder.run()
            }
        }
    }

    override fun isRunning(): Boolean {
        return (pendingAdditions.isNotEmpty()
                || pendingRemovals.isNotEmpty()
                || pendingChanges.isNotEmpty()
                || pendingMoves.isNotEmpty()
                || addAnimations.isNotEmpty()
                || removeAnimations.isNotEmpty()
                || changeAnimations.isNotEmpty()
                || moveAnimations.isNotEmpty()
                || addList.isNotEmpty()
                || changeList.isNotEmpty()
                || moveList.isNotEmpty())
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {

    }

    override fun endAnimations() {
        var count = pendingMoves.size
        for (i in count - 1 downTo 0) {
            val move = pendingMoves[i]
            val v = move.holder?.itemView
            v?.translationX = 0F
            v?.translationY = 0F
            dispatchMoveFinished(move.holder)
            pendingMoves.remove(move)
        }
        count = pendingAdditions.size
        for (i in count - 1 downTo 0) {
            val add = pendingAdditions[i]
            dispatchAddFinished(add)
            pendingAdditions.remove(add)
        }
        count = pendingRemovals.size
        for (i in count - 1 downTo 0) {
            val remove = pendingRemovals[i]
            dispatchRemoveFinished(remove)
            pendingRemovals.remove(remove)
        }
        for (i in count - 1 downTo 0) {
            val change = pendingChanges[i]
            dispatchChangeFinished(change.newHolder, false)
            pendingChanges.remove(change)
        }
        var listCount = moveList.size
        for (i in listCount - 1 downTo 0) {
            val moves = moveList[i]
            count = moves.size
            for (j in count - 1 downTo 0) {
                val move = moves[j]
                val v = move.holder?.itemView
                v?.translationX = 0F
                v?.translationY = 0F
                dispatchMoveFinished(move.holder)
                moves.remove(move)
            }
            if (moves.isEmpty()) moveList.remove(moves)
        }
        listCount = addList.size
        for (i in listCount - 1 downTo 0) {
            val adds = addList[i]
            count = adds.size
            for (j in count - 1 downTo 0) {
                val add = adds[j]
                dispatchAddFinished(add)
                adds.remove(add)
            }
            if (adds.isEmpty()) addList.remove(adds)
        }
        listCount = changeList.size
        for (i in listCount - 1 downTo 0) {
            val changes = changeList[i]
            count = changes.size
            for (j in count - 1 downTo 0) {
                val change = changes[j]
                dispatchChangeFinished(change.oldHolder, false)
                changes.remove(change)
            }
            if (changes.isEmpty()) changeList.remove(changes)
        }
        cancelAll(removeAnimations)
        cancelAll(moveAnimations)
        cancelAll(addAnimations)
        cancelAll(changeAnimations)
        dispatchAnimationsFinished()
    }

    private fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        addAnimations.add(holder)
        val v = holder.itemView
        AnimatorInflater.loadAnimator(v.context, R.animator.add_animator).apply {
            setTarget(v)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationCancel(animation: Animator?) {
                    v.alpha = 1F
                }

                override fun onAnimationEnd(animation: Animator?) {
                    animation?.removeListener(this)
                    v.alpha = 1F
                    dispatchAddFinished(holder)
                    addAnimations.remove(holder)
                    dispatchFinishedWhenDone()
                }
            })
            start()
        }
    }

    private fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        removeAnimations.add(holder)
        val v = holder.itemView
        AnimatorInflater.loadAnimator(v.context, R.animator.remove_animator).apply {
            setTarget(v)
            addListener( object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    animation?.removeListener(this)
                    v.scaleX = 1.0F
                    v.scaleY = 1.0F
                    dispatchRemoveFinished(holder)
                    removeAnimations.remove(holder)
                    dispatchFinishedWhenDone()
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
        val animator = v?.animate()
        moveInfo.holder?.let { moveAnimations.add(it) }
        animator?.setDuration(moveDuration)?.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                animation?.removeListener(this)
                dispatchMoveFinished(moveInfo.holder)
                moveAnimations.remove(moveInfo.holder)
                dispatchFinishedWhenDone()
            }
        })?.start()
    }

    private fun cancelAll(viewHolders: List<RecyclerView.ViewHolder>) {
        for (i in viewHolders.indices.reversed()) {
            viewHolders[i].itemView.animate().cancel()
        }
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
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