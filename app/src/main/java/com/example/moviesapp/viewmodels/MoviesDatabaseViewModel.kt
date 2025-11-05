package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.entitites.MovieItem
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



data class MoviesUiState(
    val movies: List<MovieItem> = emptyList(),
    val isLoading: Boolean = false
)

class MoviesDatabaseViewModel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<MoviesUiState>(MoviesUiState(isLoading = true))

    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAll().collect{ movieItems ->
                _viewState.update {
                    it.copy(movies = movieItems )
                }
            }

        }
    }

    fun insertIntoDb(title: String, voteAverage: Double, releaseDate: String, poster: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(title, voteAverage, releaseDate, poster)
        }
    }

}


class MoviesDatabaseViewModelFactory(
    private val repository: MoviesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesDatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoviesDatabaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}