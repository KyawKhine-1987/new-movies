package com.android.freelance.movies.data.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movies(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var id: Int ?= 0,
    var title: String ?= null,
    var year: Int ?= 0,
    var genre: String ?= null,
    var poster: String ?= null
)