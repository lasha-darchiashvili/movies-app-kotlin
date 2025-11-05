package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.SimilarMovies
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SimilarMoviesViewModel(val repository: MoviesRepository, similarMovieId: Int): ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()


    init {
        loadMovie(similarMovieId)
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

class SimilarMoviesViewModelFactory(private val repository: MoviesRepository, private val similarMovieId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SimilarMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SimilarMoviesViewModel(repository, similarMovieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}