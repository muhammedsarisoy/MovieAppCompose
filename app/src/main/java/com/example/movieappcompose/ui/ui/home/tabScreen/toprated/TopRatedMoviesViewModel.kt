package com.example.movieappcompose.ui.ui.home.tabScreen.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.Result
import kotlinx.coroutines.launch

class TopRatedMoviesViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _topMovie = MutableLiveData<List<Result>>()
    val topMovie: LiveData<List<Result>> get() = _topMovie

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _selectedMovieId = MutableLiveData<Int>()
    val selectedMovieId: LiveData<Int> get() = _selectedMovieId

    fun getTopRatedMovies(apiKey: String, page: Int , movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = movieRepository.getTopRatedMovies(apiKey, page , movieId)
                if (response.results.isEmpty()) {
                    _errorMessage.value = "No top rated movies found."
                } else {
                    _topMovie.value = response.results
                }
                } catch (e: Exception) {
                _errorMessage.value = "Failed to load top rated movies: ${e.message ?: "Unknown error"}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun setSelectedMovieId(id: Int) {
        _selectedMovieId.value = id
    }

}