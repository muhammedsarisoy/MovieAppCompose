package com.example.movieappcompose.ui.network

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val networkService: NetworkService) {

    suspend fun getPopularMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getPopularMovies(apiKey, page , id)
    }

    suspend fun getUpcomingMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getUpcomingMovies(apiKey, page , id)
    }

    suspend fun getTopRatedMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getTopRatedMovies(apiKey, page , id)
    }

    suspend fun getNowPlayingMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getNowPlayingMovies(apiKey, page , id)
    }

    suspend fun getLatestMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getLatestMovies(apiKey, page , id)
    }

    suspend fun getSimilarMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getSimilarMovies(apiKey, page , id)
    }

    suspend fun getRecommendedMovies(apiKey: String, page: Int , id: Int) = withContext(Dispatchers.IO) {
        networkService.getRecommendedMovies(apiKey, page , id)
    }

    suspend fun getMovieCredits(apiKey: String, movieId: Int) = withContext(Dispatchers.IO) {
        networkService.getMovieCredits(movieId, apiKey)
    }

    suspend fun getMovieVideos(apiKey: String, movieId: Int) = withContext(Dispatchers.IO) {
        networkService.getMovieVideos(movieId, apiKey)
    }

    suspend fun getSearchMovies(apiKey: String, query: String , id: Int) = withContext(Dispatchers.IO) {
        networkService.searchMovies(apiKey, query , id)
    }

    suspend fun getMovieDetail(movieId: Int, apiKey: String) = withContext(Dispatchers.IO) {
        networkService.getMovieDetail(movieId, apiKey)
    }

}