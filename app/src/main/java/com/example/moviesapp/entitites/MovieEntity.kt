package com.example.moviesapp.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val poster: String
)

