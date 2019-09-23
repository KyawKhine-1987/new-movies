package com.android.freelance.movies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.android.freelance.movies.data.db.entity.Movies

@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(mCtx: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(mCtx).also {
                instance = it
            }
        }

        private fun buildDatabase(mCtx: Context) = Room.databaseBuilder(
            mCtx.applicationContext,
            MoviesDatabase::class.java,
            "NewMovies.db"
        ).build()

    }

}