package com.example.movieappcompose.ui.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ): MovieResponse

   @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): Result

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ) : MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ) : MovieResponse

    @GET("movie/latest")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ) : MovieResponse

    @GET("movie/similar")
    suspend fun getSimilarMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ) : MovieResponse

    @GET("movie/recommended")
    suspend fun getRecommendedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("id") id: Int
    ) : MovieResponse


    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
    ): CreditsResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
    ): VideoResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("id") id: Int
    ) : MovieResponse


}