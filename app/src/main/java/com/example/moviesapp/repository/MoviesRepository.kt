package com.example.moviesapp.repository

import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieDetails
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.SearchedMovies
import com.example.moviesapp.SimilarMovies
import com.example.moviesapp.entitites.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getAllMovies(category: String? = null): AllMoviesData
    suspend fun getSingleMovie(movieId: Int): MovieDetails

    suspend fun getSimilarMovies(similarMovieId: Int): SimilarMovies

    suspend fun getMovieReviews(movieId: Int): MovieReviews

    suspend fun getSearchedMovies(movie: String): SearchedMovies

    suspend fun insert(title: String, voteAverage: Double, releaseDate: String, poster: String)
    suspend fun getAll() : Flow<List<MovieItem>>
}