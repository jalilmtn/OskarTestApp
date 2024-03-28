package com.example.oskartestapp.data.remote.dto

data class VerifiableAddresse(
    val created_at: String,
    val id: String,
    val status: String,
    val updated_at: String,
    val value: String,
    val verified: Boolean,
    val via: String
)