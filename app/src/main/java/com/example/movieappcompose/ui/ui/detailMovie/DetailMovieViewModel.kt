package com.example.movieappcompose.ui.ui.detailMovie

import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.ui.network.Credit
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.Result
import com.example.movieappcompose.ui.network.Video
import com.google.android.gms.cast.Cast
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _movieDetail = MutableLiveData<Result>()
    val movieDetail: LiveData<Result> get() = _movieDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _movieCredits = MutableLiveData<List<Credit>>()
    val movieCredits: LiveData<List<Credit>> get() = _movieCredits

    private val _movieVideos = MutableLiveData<List<Video>>()
    val movieVideos: LiveData<List<Video>> get() = _movieVideos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = movieRepository.getMovieDetail(movieId , "2766d672f73a91abc8f8a7214a9f64f7")
                _movieDetail.value = movie
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load movie details"
            }
        }
    }

    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            try {
                val credits = movieRepository.getMovieCredits("2766d672f73a91abc8f8a7214a9f64f7" , movieId)
                _movieCredits.value = credits.cast + credits.crew
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load movie credits"
            }
        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            try {
                val videos = movieRepository.getMovieVideos("2766d672f73a91abc8f8a7214a9f64f7", movieId)
                Log.d("MovieVideos", "Response: ${videos.results}")
                _movieVideos.value = videos.results
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load movie videos"
            }
        }
    }
}