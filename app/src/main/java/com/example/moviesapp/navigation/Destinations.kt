package com.example.moviesapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class MovieDetails(
    val id: Int
)

@Serializable
object Search

@Serializable
object WatchList