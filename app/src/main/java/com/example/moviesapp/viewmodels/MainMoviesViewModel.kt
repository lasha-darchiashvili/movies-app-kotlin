package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.AllMoviesData
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainMoviesViewModel(val repository: MoviesRepository, val category: String? = null   ): ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()

    init {
        loadMovies(category)
    }

    fun loadMovies(category: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = repository.getAllMovies(category)
                _viewState.value = ViewState.Success(apiResult)
            }
            catch (e: Exception) {
                _viewState.value = ViewState.Error("error")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val data: AllMoviesData?) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}

class MoviesViewModelFactory(private val repository: MoviesRepository, private val category: String? = null): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainMoviesViewModel(repository, category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}