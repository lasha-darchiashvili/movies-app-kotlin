package com.example.moviesapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moviesapp.composables.Main
import com.example.moviesapp.composables.MainScreen
import com.example.moviesapp.composables.SearchMovieComp
import com.example.moviesapp.composables.SingleMovieComp
import com.example.moviesapp.composables.WatchListComp

@Composable
fun MyNavigationHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            Main(onMovieClick = { movieId ->
                navController.navigate(MovieDetails(movieId))
            }, innerPadding = innerPadding)
        }
        composable<MovieDetails> { backStackEntry ->
            val movie: MovieDetails = backStackEntry.toRoute()
            SingleMovieComp(movie.id, navController)
        }
        composable<Search> {
            SearchMovieComp(navController)
        }
        composable<WatchList> {
            WatchListComp(navController, innerPadding = innerPadding)
        }
    }
}