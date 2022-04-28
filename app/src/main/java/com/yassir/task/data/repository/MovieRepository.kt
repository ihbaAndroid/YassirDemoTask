package com.yassir.task.data.repository

import com.yassir.task.data.model.Movies
import com.yassir.task.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun getMovieList(): Flow<Movies> = flow {
        emit(apiService.getMoviesList())
    }.flowOn(Dispatchers.IO)
}