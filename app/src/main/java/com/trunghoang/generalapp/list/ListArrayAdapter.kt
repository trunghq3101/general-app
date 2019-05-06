package com.trunghoang.generalapp.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class ListArrayAdapter(
    context: Context,
    dataSet: List<Movie>
) : ArrayAdapter<Movie>(context, 0, dataSet) {
    private var countViews = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie = getItem(position)
        var v = convertView
        val holder: ListViewHolder
        if (v == null) {
            countViews ++
            Log.d("Count Views", countViews.toString())
            v = LayoutInflater.from(context).inflate(R.layout.item5_constraint, parent, false)
            holder = ListViewHolder(v)
            v.tag = holder
        } else {
            Log.d("Count Views", "recycle")
            holder = v.tag as ListViewHolder
        }
        holder.bind(movie!!)
        return v!!
    }

    class ListViewHolder(private val itemView: View) {
        private val textTitle = itemView.textTitle
        private val imageView = itemView.imageView

        fun bind(movie: Movie) {
            textTitle.text = movie.title
            Glide.with(itemView.context)
                .load(movie.imageUrl)
                .into(imageView)
        }
    }
}