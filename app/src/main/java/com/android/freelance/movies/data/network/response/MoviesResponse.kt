package com.android.freelance.movies.data.network.response


import com.android.freelance.movies.data.db.entity.Movies
import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("data")
    val movies: List<Movies>
)