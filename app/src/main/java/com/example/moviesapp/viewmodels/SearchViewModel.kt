package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieReviews
import com.example.moviesapp.SearchedMovies
import com.example.moviesapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: MoviesRepository): ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()


    fun loadMovies(movie: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiResult = repository.getSearchedMovies(movie)
                _viewState.value = ViewState.Success(apiResult)
            }
            catch (e: Exception){
                _viewState.value = ViewState.Error("error")
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Success(val data: SearchedMovies?) : ViewState()
        data class Error(val message: String) : ViewState()
    }
}

class SearchViewModelFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}