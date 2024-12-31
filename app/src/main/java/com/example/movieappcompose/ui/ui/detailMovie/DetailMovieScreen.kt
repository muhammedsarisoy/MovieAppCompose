package com.example.movieappcompose.ui.ui.detailMovie

import android.content.Context
import android.graphics.Color
import android.hardware.camera2.CameraManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.Result
import com.example.movieappcompose.ui.network.RetrofitInstance.networkService
import com.example.movieappcompose.ui.network.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView



@Composable
fun DetailMovieScreen(
    movieId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailMovieViewModel
) {
    val movieDetail by viewModel.movieDetail.observeAsState()
    val movieVideos by viewModel.movieVideos.observeAsState()

    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
        viewModel.getMovieVideos(movieId)
    }

    if (movieVideos == null) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = movieDetail?.title ?: "Loading...",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )

            if (!movieVideos.isNullOrEmpty()) {
                VideoItem(
                    video = movieVideos!!.first(),
                    lifecyclerOwner = LocalContext.current as LifecycleOwner
                )
            } else {
                Text(
                    text = "No videos available for this movie.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Overview
                    Text(
                        text = movieDetail?.overview ?: "Loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Release Date
                    Text(
                        text = "Release Date: ${movieDetail?.release_date ?: "Loading..."}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Rating
                    Text(
                        text = "Rating: ${movieDetail?.vote_average ?: "Loading..."}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    // Popularity
                    Text(
                        text = "Popularity: ${movieDetail?.popularity ?: "Loading..."}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    // Original Language
                    Text(
                        text = "Original Language: ${movieDetail?.original_language ?: "Loading..."}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) 
        }
    }
}


@Composable
fun VideoItem(video: Video, lifecyclerOwner: LifecycleOwner) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Video: ${video.name}")
        Text(text = "Type: ${video.type}")

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    setBackgroundColor(Color.BLACK)
                    lifecyclerOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(video.key, 0f)
                            youTubePlayer.pause()
                        }
                    })
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailMovieScreenPreview() {
    DetailMovieScreen(movieId = 1, navController = NavController(LocalContext.current), viewModel = DetailMovieViewModel(MovieRepository(networkService)))
}
