package com.example.oskartestapp.data.remote.dto

data class ContinueWith(
    val action: String,
    val flow: Flow,
    val ory_session_token: String
)