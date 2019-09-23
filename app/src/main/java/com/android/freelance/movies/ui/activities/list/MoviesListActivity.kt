package com.android.freelance.movies.ui.activities.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.freelance.movies.R
import com.android.freelance.movies.data.db.entity.Movies
import com.android.freelance.movies.ui.activities.detail.MoviesDetailActivity
import com.android.freelance.movies.ui.adapter.MoviesAdapter
import com.android.freelance.movies.ui.viewmodel.MoviesViewModel
import com.android.freelance.movies.ui.viewmodel.MoviesViewModelFactory
import com.android.freelance.wonders.ui.util.Coroutines
import kotlinx.android.synthetic.main.activity_movieslist.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MoviesListActivity : AppCompatActivity(), MoviesAdapter.ListItemClickListener, KodeinAware {

    private val LOG_TAG = MoviesListActivity::class.java.name

    override val kodein by kodein()
    private val factory: MoviesViewModelFactory by instance()
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movieslist)

        viewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel::class.java)

        // ProgressBar
        pbLoadingIndicator.visibility = View.VISIBLE

        bindUI()

        offlineData()
    }

    private fun bindUI() {
        Log.i(LOG_TAG, "TEST: bindUI() is called...")

        pbLoadingIndicator.visibility = View.GONE
        viewModel.callWebService
    }

    private fun offlineData() = Coroutines.main {
        Log.i(LOG_TAG, "TEST: offlineData() is called...")

        viewModel.getAllMovies.await().observe(this, Observer {
            pbLoadingIndicator.visibility = View.GONE

            refreshUIWith(it)
        })
    }

    private fun refreshUIWith(movies: List<Movies>) {
        Log.i(LOG_TAG, "TEST: refreshUIWith() is called...")

        // try to touch View of UI thread
        this@MoviesListActivity.runOnUiThread(java.lang.Runnable {
            val layoutManager = LinearLayoutManager(this)
            rvMoviesList.layoutManager = layoutManager
            rvMoviesList.hasFixedSize()
            rvMoviesList.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            val adapter =
                MoviesAdapter(this@MoviesListActivity, applicationContext, movies)
            rvMoviesList.adapter = adapter
        })
    }

    override fun onListItemClick(position: Int, moviesEntity: List<Movies>) {
        Log.i(LOG_TAG, "TEST: onListItemClick() called...")

        val intent = Intent(applicationContext, MoviesDetailActivity::class.java)
        intent.putExtra("image", moviesEntity[position].poster)
        intent.putExtra("title", moviesEntity[position].title)
        intent.putExtra("year", moviesEntity[position].year.toString())
        startActivity(intent)
    }
}
