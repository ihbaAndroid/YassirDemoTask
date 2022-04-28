package com.yassir.task.data.network

import com.yassir.task.data.model.Movies
import retrofit2.http.GET

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500/"
    }

    @GET("3/discover/movie?api_key=c9856d0cb57c3f14bf75bdc6c063b8f3")
    suspend fun getMoviesList(): Movies
}