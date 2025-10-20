package com.example.moviesapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Movie(val id: Int,
                 @SerialName("poster_path")
                 val posterPath: String,
                 @SerialName("backdrop_path")
                 val backdropPath: String,
                 val title: String
)

@Serializable
data class Review(val id: String,
                 val content: String,
                 val author: String
)

@Serializable
data class AllMoviesData(
    val results: List<Movie>
)

@Serializable
data class SimilarMovies(
    val results: List<Movie>
)

@Serializable
data class MovieReviews(
    val results: List<Review>
)