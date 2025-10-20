package com.example.moviesapp.repository

import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.SimilarMovies

interface MoviesRepository {
    suspend fun getAllMovies(): AllMoviesData
    suspend fun getSingleMovie(): Movie

    suspend fun getDetailsMovies(): SimilarMovies

    suspend fun getMovieReviews(): MovieReviews
}