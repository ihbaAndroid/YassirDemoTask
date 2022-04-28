package com.yassir.task.utils

import com.yassir.task.data.model.Movies

sealed class ApiResponse<out T> {

    data class Success<R>(val data: R) : ApiResponse<R>()
    data class Failure(val msg: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()

}


sealed class ApiStates {

    data class Success(val data: Movies) : ApiStates()
    data class Failure(val msg: Throwable) : ApiStates()
    object Loading : ApiStates()
    object Empty : ApiStates()
}
