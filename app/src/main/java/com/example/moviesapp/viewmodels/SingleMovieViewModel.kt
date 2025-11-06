package com.example.moviesapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.moviesapp.Movie
import com.example.moviesapp.MovieDetails
import com.example.moviesapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(val repository: MoviesRepository, val savedStateHandle: SavedStateHandle): ViewModel() {

    val movie: com.example.moviesapp.navigation.MovieDetails = savedStateHandle.toRoute()

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()

    init {
        loadMovie(movie.id)
    }
    fun loadMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = repository.getSingleMovie(movieId)
                _viewState.value = ViewState.Success(apiResult)
            } catch (e: Exception) {
                _viewState.value = ViewState.Error("error")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val data: MovieDetails?) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}

//class SingleMovieViewModelFactory(private val repository: MoviesRepository, private val movieId: Int): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(SingleMovieViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SingleMovieViewModel(repository, movieId) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//
//}