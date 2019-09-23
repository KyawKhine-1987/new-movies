package com.android.freelance.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.android.freelance.movies.data.repository.MoviesRepository
import com.android.freelance.wonders.ui.util.lazyDeferred

class MoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel(){

    val callWebService = repository.service

    val getAllMovies by lazyDeferred {
        repository.getAllMovies()
    }
}