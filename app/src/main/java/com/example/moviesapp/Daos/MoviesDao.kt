package com.example.moviesapp.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.entitites.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao

interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieItem: MovieItem)
    @Query("SELECT * FROM Movies")
    fun getMovies(): Flow<List<MovieItem>>
    @Query("DELETE FROM Movies")
    suspend fun clear()
}