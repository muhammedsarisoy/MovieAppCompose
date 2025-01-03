package com.example.movieappcompose.ui.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieappcompose.ui.network.MovieRepository
import com.example.movieappcompose.ui.network.RetrofitInstance.networkService
import com.example.movieappcompose.ui.ui.detail.DetailScreen
import com.example.movieappcompose.ui.ui.detail.DetailScreenViewModel
import com.example.movieappcompose.ui.ui.detailMovie.DetailMovieScreen
import com.example.movieappcompose.ui.ui.detailMovie.DetailMovieViewModel
import com.example.movieappcompose.ui.ui.home.HomeScreen
import com.example.movieappcompose.ui.ui.home.HomeViewModel
import com.example.movieappcompose.ui.ui.home.tabScreen.popularScreen.PopularMoviesScreen
import com.example.movieappcompose.ui.ui.home.tabScreen.popularScreen.PopularMoviesViewModel
import com.example.movieappcompose.ui.ui.home.tabScreen.toprated.TopRatedMoviesScreen
import com.example.movieappcompose.ui.ui.home.tabScreen.toprated.TopRatedMoviesViewModel


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable("HomeScreen") {
            HomeScreen(
                navController = navController,
                modifier = modifier,
                viewModel = HomeViewModel(MovieRepository(networkService))
            )
        }

        composable("DetailScreen") {
            DetailScreen(
                navController = navController,
                modifier = modifier,
                viewModel = DetailScreenViewModel(MovieRepository(networkService))
            )
        }


        composable("PopularMoviesScreen") {
            PopularMoviesScreen(
                navController = navController,
                modifier = modifier,
                viewModel = PopularMoviesViewModel(MovieRepository(networkService))
            )
        }

        composable("TopRatedMoviesScreen") {
            TopRatedMoviesScreen(
                navController = navController,
                modifier = modifier,
                viewModel = TopRatedMoviesViewModel(MovieRepository(networkService))
            )
        }

        composable(
            route = "DetailMovieScreen/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailMovieScreen(
                movieId = movieId,
                navController = navController,
                modifier = modifier,
                viewModel = DetailMovieViewModel(MovieRepository(networkService))
            )
        }
    }
}
