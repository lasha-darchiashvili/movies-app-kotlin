package com.example.moviesapp.repository

import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.api.MoviesApi
import com.example.moviesapp.SimilarMovies
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MoviesRepositoryImpl(val category: String? = null,
                           val movieId: Int? = null,
                           val similarMovieId: Int? = null
//                           val detailsTabName: String? = null
): MoviesRepository {


    val json = Json { ignoreUnknownKeys = true }

    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api = retrofit.create(MoviesApi::class.java)

    override suspend fun getAllMovies(): AllMoviesData {
        val response = api.getAllMovies(category)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getSingleMovie(): Movie {
        val response = api.getSingleMovie(movieId)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getDetailsMovies(): SimilarMovies {
        val response = api.getSimilarMovies(similarMovieId)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getMovieReviews(): MovieReviews {
        val response = api.getMovieReviews(similarMovieId)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }
}