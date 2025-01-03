package com.example.movieappcompose.ui.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.format


class DetailScreenViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _allMovies = MutableLiveData<List<Result>>()
    val allMovies: LiveData<List<Result>> get() = _allMovies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _selectedMovieId = MutableLiveData<Int>()
    val selectedMovieId: LiveData<Int> get() = _selectedMovieId

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var currentPage = 1

    private val allMoviesList = mutableListOf<Result>()

    fun getAllMovies(apiKey: String , movieId: Int) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val deferredUpcoming =
                    async { movieRepository.getUpcomingMovies(apiKey, currentPage , movieId) }
                val deferredTopRated =
                    async { movieRepository.getTopRatedMovies(apiKey, currentPage, movieId) }
                val deferredPopular =
                    async { movieRepository.getPopularMovies(apiKey, currentPage, movieId) }

                val upcomingMoviesCall = deferredUpcoming.await()
                val topRatedMoviesCall = deferredTopRated.await()
                val popularMoviesCall = deferredPopular.await()

                allMoviesList.clear()
                allMoviesList.addAll(upcomingMoviesCall.results)
                allMoviesList.addAll(topRatedMoviesCall.results)
                allMoviesList.addAll(popularMoviesCall.results)

                _allMovies.value = allMoviesList

            } catch (e: Exception) {
                _errorMessage.value = "Filmler yüklenirken hata oluştu: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun searchMovies(query: String , movieId: Int) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    _loading.value = true
                    val searchResults = movieRepository.getSearchMovies("2766d672f73a91abc8f8a7214a9f64f7",query ,movieId)
                    _allMovies.value = searchResults.results

                } catch (e: Exception) {
                    _errorMessage.value = "Arama sırasında hata oluştu: ${e.message}"
                } finally {
                    _loading.value = false
                }
            }
        } else {
            _allMovies.value = emptyList()
        }
    }

    fun setSelectedMovieId(id: Int) {
        _selectedMovieId.value = id
    }
}
