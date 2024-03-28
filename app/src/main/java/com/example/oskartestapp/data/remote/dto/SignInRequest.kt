package com.example.oskartestapp.data.remote.dto

data class SignInRequest(
    val identifier: String,
    val password: String,
    val method: String = "password"
)