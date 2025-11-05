package com.example.moviesapp

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Movie(val id: Int,
                 @SerialName("poster_path")
                 val posterPath: String?,
                 @SerialName("backdrop_path")
                 val backdropPath: String?,
                 val title: String?,
)

@Serializable
data class MovieDetails(val id: Int,
                 @SerialName("poster_path")
                 val posterPath: String?,
                 @SerialName("backdrop_path")
                 val backdropPath: String?,
                 val runtime: Int?,
                 @SerialName("release_date")
                 val releaseDate: String?,
                 val title: String?,
                 val genres: List<Genre>? = emptyList(),
                 @SerialName("vote_average")
                 val voteAverage: Double?,
                 val overview: String?

)

@Serializable
data class Review(val id: String?,
                 val content: String?,
                 val author: String?,
                 @SerialName("author_details")
                 val authorDetails: AuthorDetail?,
)

@Serializable
data class SearchedMovie(val id: Int,
                 @SerialName("poster_path")
                 val posterPath: String?,
                 @SerialName("backdrop_path")
                 val backdropPath: String?,
                 val title: String?,
                         @SerialName("release_date")
                         val releaseDate: String?,
                         @SerialName("vote_average")
                         val voteAverage: Double?,

)


@Serializable
data class AllMoviesData(
    val results: List<Movie>?
)

@Serializable
data class SimilarMovies(
    val results: List<Movie>
)

@Serializable
data class SearchedMovies(
    val results: List<SearchedMovie>
)

@Serializable
data class MovieReviews(
    val results: List<Review>
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class AuthorDetail(
    val rating: Double?,
)