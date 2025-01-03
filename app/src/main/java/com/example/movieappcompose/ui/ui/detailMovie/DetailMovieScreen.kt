package com.example.movieappcompose.ui.ui.detailMovie

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappcompose.ui.network.Credit
import com.example.movieappcompose.ui.network.MovieRepository
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
    val movieCredits by viewModel.movieCredits.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
        viewModel.getMovieCredits(movieId)
        viewModel.getMovieVideos(movieId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (movieVideos == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = movieDetail?.title ?: "Loading...",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1.0f)
                    )
                }

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
                        Text(
                            text = movieDetail?.overview ?: "Loading...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Info",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Rating: ${movieDetail?.vote_average ?: "Loading..."}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Release Date: ${movieDetail?.release_date ?: "Loading..."}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Cast(modifier = Modifier.fillMaxWidth(), detailMovieViewModel = movieCredits)
            }
        }
    }
}



@Composable
fun Cast(
    modifier: Modifier = Modifier,
    detailMovieViewModel: List<Credit>?
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (detailMovieViewModel?.isNotEmpty() == true) {
                items(detailMovieViewModel) { credit ->
                    ElevatedCard(
                        modifier = Modifier
                            .size(width = 150.dp, height = 150.dp)
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            AsyncImage(
                                model = credit.profile_path.let { "https://image.tmdb.org/t/p/w500$it" },
                                contentDescription = "Profile picture of ${credit.name}",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = credit.name ?: "Unknown Name",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = credit.character ?: "Unknown Character",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "No cast available for this movie.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun VideoItem(video: Video, lifecyclerOwner: LifecycleOwner) {
    Column(modifier = Modifier.padding(8.dp)) {
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
