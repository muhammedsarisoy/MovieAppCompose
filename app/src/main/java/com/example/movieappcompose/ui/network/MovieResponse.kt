package com.example.movieappcompose.ui.network

import com.google.android.gms.cast.Cast

data class MovieResponse(
    val page: Int,
    val results: List<Result>,
)

data class CreditsResponse(
    val id: Int,
    val cast: List<Credit>,
    val crew: List<Credit>
)

data class Credit(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String
)

data class VideoResponse(
    val id: Int,
    val results: List<Video>
)

data class Video(
    val id: String,
    val name: String,
    val site: String,
    val key: String,
    val type: String
)