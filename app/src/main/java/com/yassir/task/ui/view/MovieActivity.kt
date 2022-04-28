package com.yassir.task.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yassir.task.data.model.Movies
import com.yassir.task.data.network.ApiService
import com.yassir.task.ui.viewmodel.MovieViewModel
import com.yassir.task.ui.theme.MovieAppTheme
import com.yassir.task.utils.ApiStates
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        getAllMovies(viewModel = viewModel, this@MovieActivity)
                    }

                }
            }
        }
    }


}

@ExperimentalMaterialApi
@Composable
fun getAllMovies(viewModel: MovieViewModel, context: Context) {
    when (val result = viewModel.response.value) {
        is ApiStates.Success -> {
            LazyColumn {
                items(result.data.results) { response ->
                    MovieList(response) {
                        onGettingClick(response, context)
                    }
                }
            }
        }
        is ApiStates.Failure -> {
            Text(text = "${result.msg}")
        }
        ApiStates.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        }
        ApiStates.Empty -> {

        }
    }

}


@ExperimentalMaterialApi
@Composable
fun MovieList(movieInfo: Movies.Results, onGettingClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        //elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = androidx.compose.foundation.shape.CornerSize(2.dp)),
        onClick = { onGettingClick() }

    ) {
        Row {
            GlideImage(imageModel = "${ApiService.BASE_POSTER_URL}${movieInfo.poster_path}",
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = movieInfo.original_title!!, style = typography.h6)
                Text(text = movieInfo.overview!!, style = typography.caption)

            }
        }
    }
}


private fun onGettingClick(results: Movies.Results, context: Context) {
    val intent = Intent(context, MovieDetailActivity::class.java).apply {
        putExtra("image", "${ApiService.BASE_POSTER_URL}${results.poster_path}")
        putExtra("title", results.original_title)
        putExtra("rating", results.vote_average)
        putExtra("description", results.overview)
    }
    context.startActivity(intent)
}