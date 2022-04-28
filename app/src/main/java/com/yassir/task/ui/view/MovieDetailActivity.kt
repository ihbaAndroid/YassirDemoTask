package com.yassir.task.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yassir.task.data.network.ApiService
import com.yassir.task.ui.theme.MovieAppTheme
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme() {
                androidx.compose.material.Surface {
                    val bundle = intent.extras
                    val image = bundle?.getString("image")
                    val name = bundle?.getString("title")
                    val rating = bundle?.getString("rating")
                    val description = bundle?.getString("description")
                    MovieDetails(name!!, image!!, rating!!, description!!)
                }
            }
        }
    }
}

@Composable
fun MovieDetails(name: String, image: String, rating: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        GlideImage(imageModel = "${ApiService.BASE_POSTER_URL}${image}",
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally).aspectRatio(1.2148f)
        )
        Text(text = name, style = MaterialTheme.typography.h6)
        Text(text = rating, style = MaterialTheme.typography.caption)
        Text(
            text = description,
            style = MaterialTheme.typography.caption
        )

    }
}