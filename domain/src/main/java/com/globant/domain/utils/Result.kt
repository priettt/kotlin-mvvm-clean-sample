package com.globant.domain.utils

sealed class Result<out T : Any> {
    open class Success<out T : Any>(val data: T) : Result<T>()
    open class Failure(val exception: Exception) : Result<Nothing>()
}
