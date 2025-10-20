package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moviesapp.composables.Main
import com.example.moviesapp.composables.SingleMovieComp

@Composable
fun MyNavigationHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            Main(onMovieClick = { movieId ->
                navController.navigate(Detail(movieId))
            })
        }
        composable<Detail> { backStackEntry ->
            val movie: Detail = backStackEntry.toRoute()
            SingleMovieComp(movie.id, navController)
        }
    }
}