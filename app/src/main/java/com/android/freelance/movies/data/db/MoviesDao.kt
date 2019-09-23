package com.android.freelance.movies.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.freelance.movies.data.db.entity.Movies

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movies>)

    @Query("select * from movies nolock order by id asc;")
    fun fetchAllWonders(): LiveData<List<Movies>>

    @Query("delete from movies;")
    suspend fun deleteAllWonders()
}