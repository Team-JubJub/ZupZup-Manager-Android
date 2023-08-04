package com.example.zupzup_manager.domain

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val failureCode: Int) : Response<Nothing>()
    data class Exception(val exception: Throwable) : Response<Nothing>()
}