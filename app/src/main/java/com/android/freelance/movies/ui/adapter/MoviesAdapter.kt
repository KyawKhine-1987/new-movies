package com.android.freelance.movies.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.freelance.movies.R
import com.android.freelance.movies.data.db.entity.Movies
import com.bumptech.glide.Glide

class MoviesAdapter(
    val mListItemClickListener: ListItemClickListener,
    private val context: Context,
    private val movies: List<Movies>
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val LOG_TAG = MoviesAdapter::class.java.name

    interface ListItemClickListener {
        fun onListItemClick(clickItemIndex: Int, moviesEntity: List<Movies>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        Log.i(LOG_TAG, "TEST: onCreateViewHolder() is called...")

        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_movies_list, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, i: Int) {
        Log.i(LOG_TAG, "TEST: onBindViewHolder() is called...")

        holder.bindModel(movies[i])
    }


    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val LOG_TAG = MoviesViewHolder::class.java.name

        init {
            itemView.setOnClickListener(this)
        }

        private val moviesImage: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val tvGenre: TextView = itemView.findViewById(R.id.tvMovieGenre)
        private val tvYear: TextView = itemView.findViewById(R.id.tvMovieYear)

        @SuppressLint("SetTextI18n")
        fun bindModel(movies: Movies) {
            Log.i(LOG_TAG, "TEST: bindModel() is called...")

            Glide.with(context).load(movies.poster).into(moviesImage)
            tvTitle.text = "Movie Name : " + movies.title
            tvGenre.text = "Movie Genre : " + movies.genre
            tvYear.text = "Released Year : " + movies.year
        }

        override fun onClick(v: View?) {
            Log.i(LOG_TAG, "TEST: onClick() is called...")

            val clickedPosition = getAdapterPosition()
            mListItemClickListener.onListItemClick(clickedPosition, movies)
        }
    }
}