package com.example.oskartestapp.data.remote.dto

data class SignUpResponse(
    val continue_with: List<ContinueWith>,
    val identity: Identity,
    val session: Session,
    val session_token: String
)