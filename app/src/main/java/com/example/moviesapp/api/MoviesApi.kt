package com.example.moviesapp.api

import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieDetails
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.SearchedMovies
import com.example.moviesapp.SimilarMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "78828dca7949b70ca50313e4d49660d1"


interface MoviesApi {
    @GET("3/movie/{type}?api_key=${API_KEY}")
    suspend fun getAllMovies(@Path("type") movieType: String?): Response<AllMoviesData>
    @GET("3/movie/{id}?api_key=${API_KEY}")
    suspend fun getSingleMovie(@Path("id") characterId: Int?): Response<MovieDetails>

    @GET("3/movie/{id}/similar?api_key=${API_KEY}")
    suspend fun getSimilarMovies(@Path("id") characterId: Int?): Response<SimilarMovies>

    @GET("3/movie/{id}/reviews?api_key=${API_KEY}")
    suspend fun getMovieReviews(@Path("id") characterId: Int?): Response<MovieReviews>

    @GET("3/search/movie?include_adult=false&language=en-US&page=1&api_key=$API_KEY")
    suspend fun getSearchedMovies(@Query("query") movie: String): Response<SearchedMovies>
}

interface SingleMovie {
    @GET("3/movie/{id}?api_key=${API_KEY}")
    suspend fun getSingleMovie(@Path("id") characterId: Int): Response<Movie>
}