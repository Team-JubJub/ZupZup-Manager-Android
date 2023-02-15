package com.example.zupzup_manager.domain

sealed class DataResult<out T> {
    data class Success<out T> (val data : T) : DataResult<T>()
    data class Failure(val errorCode : Int) : DataResult<Nothing>()
}
