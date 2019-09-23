package com.android.freelance.movies.data.network

import com.android.freelance.movies.data.network.response.MoviesResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val baseUrl = "https://raw.githubusercontent.com/filippella/Sample-API-Files/master/json/"
interface ApiMovies {

    @GET("movies-api.json")
    fun getMovies(): Observable<MoviesResponse>

    companion object {

        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiMovies {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiMovies::class.java)
        }
    }
}