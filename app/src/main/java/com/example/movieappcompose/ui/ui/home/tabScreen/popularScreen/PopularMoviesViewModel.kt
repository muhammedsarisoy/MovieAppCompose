package com.example.movieappcompose.ui.ui.home.tabScreen.popularScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.Result
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _popularMovies = MutableLiveData<List<Result>>()
    val popularMovies: LiveData<List<Result>> get() = _popularMovies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _selectedMovieId = MutableLiveData<Int>()
    val selectedMovieId: LiveData<Int> get() = _selectedMovieId


    fun getPopularMovies(apiKey: String, page: Int, movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = movieRepository.getPopularMovies(apiKey, page , movieId)
                if (response.results.isEmpty()) {
                    _errorMessage.value = "No popular movies found."
                } else {
                    _popularMovies.value = response.results
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load popular movies: ${e.message ?: "Unknown error"}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun setSelectedMovieId(id: Int) {
        _selectedMovieId.value = id
    }

}