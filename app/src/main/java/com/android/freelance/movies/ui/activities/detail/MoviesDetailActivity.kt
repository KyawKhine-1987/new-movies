package com.android.freelance.movies.ui.activities.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.freelance.movies.R
import com.bumptech.glide.Glide
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MoviesDetailActivity : AppCompatActivity(), KodeinAware {

    private val LOG_TAG = MoviesDetailActivity::class.java.name
    override val kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moviesdetail)

        relevantDataBindingInUI()
    }

    @SuppressLint("SetTextI18n")
    private fun relevantDataBindingInUI() {
        Log.i(LOG_TAG, "TEST: relevantDataBindingInUI() is called...")

        val moviesDetailImage: ImageView = this.findViewById(R.id.ivDetailMovies)
        val moviesDetailTitle: TextView = this.findViewById(R.id.tvDetailTitle)
        val moviesDetailYear: TextView = this.findViewById(R.id.tvDetailYear)

        val imageData = intent.getStringExtra("image")
        Glide.with(this).load(imageData).into(moviesDetailImage)
        moviesDetailTitle.text = "Movie Name : " + intent.getStringExtra("title")
        moviesDetailYear.text = "Released Year : " + intent.getStringExtra("year")
    }
}
