package com.example.oskartestapp.data.remote.dto

data class RegistrationRequest(
    val csrf_token: String,
    val method: String = "password",
    val password: String,
    val traits: Traits
)