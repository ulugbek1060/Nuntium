package com.example.nuntium.models

sealed class HeadlineRecourse<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : HeadlineRecourse<T>(data)
    class Error<T>(message: String, data: T?) : HeadlineRecourse<T>(data, message)
    class Loading<T> : HeadlineRecourse<T>()
}