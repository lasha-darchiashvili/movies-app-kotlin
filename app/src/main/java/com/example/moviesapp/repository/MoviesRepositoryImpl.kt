package com.example.moviesapp.repository

import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.Daos.MoviesDao
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieDetails
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.SearchedMovies
import com.example.moviesapp.api.MoviesApi
import com.example.moviesapp.SimilarMovies
import com.example.moviesapp.entitites.MovieItem
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.text.insert

class MoviesRepositoryImpl(private val dao: MoviesDao? = null
): MoviesRepository {


    val json = Json { ignoreUnknownKeys = true }

    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val api = retrofit.create(MoviesApi::class.java)

    override suspend fun getAllMovies(category: String?): AllMoviesData {
        val response = api.getAllMovies(category)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getSingleMovie(movieId: Int): MovieDetails {
        println("Fetching movie $movieId")
        val response = api.getSingleMovie(movieId)
        if(response.isSuccessful) {
            println("Movie response: ${response.body()}")
            return response.body()!!
        }
        else {
            println("Error response: ${response.code()}")
            throw Exception("error")
        }
    }

    override suspend fun getSimilarMovies(similarMovieId: Int): SimilarMovies {
        val response = api.getSimilarMovies(similarMovieId)
        if(response.isSuccessful) {
            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getMovieReviews(movieId: Int): MovieReviews {
        val response = api.getMovieReviews(movieId)
        if(response.isSuccessful) {
            println("rev response: ${response.body()}")

            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun getSearchedMovies(movie: String): SearchedMovies {
        val response = api.getSearchedMovies(movie)
        if(response.isSuccessful) {
            println("rev response: ${response.body()}")

            return response.body()!!
        }
        else throw Exception("error")
    }

    override suspend fun insert(title: String, voteAverage: Double, releaseDate: String, poster: String) {
        val movieItem = MovieItem(0, title, voteAverage, releaseDate, poster)
        dao?.insert(movieItem)
    }

    override suspend fun getAll(): Flow<List<MovieItem>> {
        return dao?.getMovies()!!
    }
}