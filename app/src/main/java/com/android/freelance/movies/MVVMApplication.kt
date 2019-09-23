package com.android.freelance.movies

import android.app.Application
import com.android.freelance.movies.data.db.MoviesDatabase
import com.android.freelance.movies.data.network.ApiMovies
import com.android.freelance.movies.data.network.NetworkConnectionInterceptor
import com.android.freelance.movies.data.repository.MoviesRepository
import com.android.freelance.movies.ui.viewmodel.MoviesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication  : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiMovies(instance()) }
        bind() from singleton { MoviesDatabase(instance()) }
        bind() from singleton { MoviesRepository(instance(), instance(), instance()) }
        bind() from provider { MoviesViewModelFactory(instance()) }
    }
}