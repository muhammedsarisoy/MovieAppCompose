package com.example.movieappcompose.ui.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel,
) {

    var searchQuery by remember { mutableStateOf("") }
    val allMovies by viewModel.allMovies.observeAsState(emptyList())
    val favorites by viewModel.favorites.observeAsState(emptyList())
    val selectedMovieId by viewModel.selectedMovieId.observeAsState(0)


    LaunchedEffect(Unit) {
        viewModel.getAllMovies("2766d672f73a91abc8f8a7214a9f64f7" , selectedMovieId)
    }

    Box(modifier = Modifier.fillMaxSize()){
        if (allMovies.isEmpty()){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }else{
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            if (it.isNotBlank()) {
                                viewModel.searchMovies(it, selectedMovieId)
                            }
                        },
                        label = { Text("Search Movies") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )

                    IconButton(onClick = {
                        viewModel.searchMovies(searchQuery , selectedMovieId)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(allMovies) { movie ->
                        ElevatedCard(
                            onClick = {
                                viewModel.setSelectedMovieId(movie.id)
                                navController.navigate("DetailMovieScreen/${movie.id}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(width = 400.dp, height = 100.dp)
                        ) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(movie.title, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        movie.overview,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                IconButton(onClick = {
                                    viewModel.toggleFavorite(movie)
                                }) {
                                    val isFavorite = favorites.contains(movie)
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
