package com.yassir.task.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {

    emit(ApiResponse.Loading)

    try {
        val response = call()

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResponse.Success(it))
            }
        } else {
            response.errorBody()?.let { error ->
                error.close()
                emit(ApiResponse.Failure(error.string()))
            }
        }

    } catch (e: Exception) {
        emit(ApiResponse.Failure(e.message.toString()))
    }

}