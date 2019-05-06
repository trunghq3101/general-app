package com.trunghoang.generalapp.recyclerAnimation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAnimationAdapter(
    private val dataSet: List<Movie>
) : RecyclerView.Adapter<MovieAnimationAdapter.MovieViewHolder>() {
    companion object {
        const val ACTION_LIKE_IMAGE = "Action_Like_Image"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun setData(newDataSet: List<Movie>) {
        (dataSet as ArrayList).clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    fun addItem(newItem: Movie) {
        (dataSet as ArrayList).add(1, newItem)
        notifyItemInserted(1)
    }

    fun removeItem(position: Int) {
        (dataSet as ArrayList).removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateLike(position: Int) {
        val currentLikeStatus = dataSet[position].isLiked
        dataSet[position].isLiked = !currentLikeStatus!!
        notifyItemChanged(position, ACTION_LIKE_IMAGE)
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textTitle = itemView.textTitle
        private val imageView = itemView.imageView
        private val imageFavorite = itemView.imageFavorite
        private val unlikeDrawable = itemView.context.getDrawable(R.drawable.ic_favorite_border)
        private val likedDrawable = itemView.context.getDrawable(R.drawable.ic_favorite)

        fun bind(movie: Movie) {
            textTitle.text = movie.title
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(imageView)
            imageFavorite.setImageDrawable(
                when (movie.isLiked) {
                    true -> likedDrawable
                    false -> unlikeDrawable
                }
            )
        }

        fun animateLike(itemAnimator: RecyclerView.ItemAnimator) {
            AnimatorInflater.loadAnimator(itemView.context, R.animator.like_animator).apply {
                setTarget(itemView.imageFavoriteAnimation)
                addListener( object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        itemAnimator.dispatchAnimationFinished(this@MovieViewHolder)
                    }
                })
                start()
            }
        }
    }
}