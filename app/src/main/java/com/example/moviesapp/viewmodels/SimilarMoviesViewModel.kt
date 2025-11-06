package com.example.moviesapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.moviesapp.SimilarMovies
import com.example.moviesapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(val repository: MoviesRepository, val savedStateHandle: SavedStateHandle): ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()

    val movie: com.example.moviesapp.navigation.MovieDetails = savedStateHandle.toRoute()


    init {
        loadMovie(movie.id)
    }

    fun loadMovie(similarMovieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = repository.getSimilarMovies(similarMovieId)
                _viewState.value = ViewState.Success(apiResult)
            }
            catch (e: Exception){
                _viewState.value = ViewState.Error("error")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val data: SimilarMovies?) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}

//class SimilarMoviesViewModelFactory(private val repository: MoviesRepository, private val similarMovieId: Int): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(SimilarMoviesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SimilarMoviesViewModel(repository, similarMovieId) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//
//}