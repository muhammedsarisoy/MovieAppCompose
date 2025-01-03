package com.example.movieappcompose.ui.ui.home

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.example.movieappcompose.R


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val upcomingMovies by viewModel.upcomingMovies.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val selectedMovieId by viewModel.selectedMovieId.observeAsState(0)
    val loading by viewModel.loading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.getUpcomingMovies("2766d672f73a91abc8f8a7214a9f64f7", 1 ,selectedMovieId )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
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
                modifier = Modifier.fillMaxSize()
            ) {
                TabLayout(navController = navController)
                Text(
                    text = "Movies currently in theaters:",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                    color = MaterialTheme.colorScheme.primary,
                )

                AnimatedCarousel(
                    viewModel = viewModel,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Upcoming Movies:",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(upcomingMovies) { movie ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clickable {
                                    viewModel.setSelectedMovieId(movie.id)
                                    navController.navigate("DetailMovieScreen/${movie.id}")
                                }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                    contentDescription = "Movie Poster",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(
                                        text = movie.overview,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Text(
                                        text = "Release Date: ${movie.release_date}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabLayout(navController: NavController) {
    val tabs = listOf("Upcoming", "Popular", "Top Rated")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            val selectedTab = tabPositions[selectedTabIndex]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = selectedTab.left)
                    .width(selectedTab.width)
                    .height(4.dp)
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    when (title) {
                        "Upcoming" -> {
                            navController.navigate("HomeScreen")
                        }
                        "Popular" -> {
                            navController.navigate("PopularMoviesScreen")
                        }
                        "Top Rated" -> {
                            navController.navigate("topRatedMoviesScreen")
                        }
                    }
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCarousel(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val movies by viewModel.upcomingMovies.observeAsState(emptyList())
    val animatedScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            animatedScale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )
            animatedScale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
            )
        }
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { movies.size },
        modifier = modifier,
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { index ->
        if (index < movies.size) {
            val movie = movies[index]
            Image(
                painter = rememberAsyncImagePainter(model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"),
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .graphicsLayer(
                        scaleX = animatedScale.value,
                        scaleY = animatedScale.value
                    ),
                contentScale = ContentScale.Crop,
                contentDescription = movie.title
            )
        } else {
            Text(text = "Invalid movie index", style = MaterialTheme.typography.bodySmall)
        }
    }
}



