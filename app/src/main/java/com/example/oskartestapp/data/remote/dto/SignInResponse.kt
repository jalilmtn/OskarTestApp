package com.example.oskartestapp.data.remote.dto

data class SignInResponse(
    val continue_with: Any,
    val session: Session,
    val session_token: String
)