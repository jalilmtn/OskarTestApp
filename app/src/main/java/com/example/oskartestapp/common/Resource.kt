package com.example.oskartestapp.common

sealed class Resource<T>(val data: T? = null, val  message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(errorResource: ErrorResource) : Resource<T>(message = errorResource.message)
    class Loading<T> : Resource<T>()

}

enum class ErrorResource(val message: String) {
    IO_ERROR("heck your internet connection"),
    HTTP_ERROR("Something went wrong, please try again"),
    ERROR("Something went wrong")
}
