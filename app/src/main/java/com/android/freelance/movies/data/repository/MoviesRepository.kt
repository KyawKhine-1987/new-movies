package com.android.freelance.movies.data.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.android.freelance.movies.data.db.MoviesDatabase
import com.android.freelance.movies.data.db.entity.Movies
import com.android.freelance.movies.data.network.ApiMovies
import com.android.freelance.wonders.ui.util.Coroutines
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val api: ApiMovies,
    private val db: MoviesDatabase,
    mCtx : Context
) {

    val service = api.getMovies()
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({

            val moviesListFromNetwork = it.movies

            val moviesListEntity = ArrayList<Movies>()
            for (moviesFromNetwork in moviesListFromNetwork) {

                val movies = Movies()
                movies.id = moviesFromNetwork.id
                movies.title = moviesFromNetwork.title
                movies.year = moviesFromNetwork.year
                movies.genre = moviesFromNetwork.genre
                movies.poster = moviesFromNetwork.poster
                moviesListEntity.add(movies)
            }

            Coroutines.io {
                db.moviesDao().deleteAllWonders()
                db.moviesDao().insert(moviesListEntity)
            }
        }, {
            Toast.makeText(
                mCtx,
                it.message,
                Toast.LENGTH_LONG
            ).show()
        })


    suspend fun getAllMovies(): LiveData<List<Movies>> {
        return withContext(Dispatchers.IO) {
            db.moviesDao().fetchAllWonders()
        }
    }
}