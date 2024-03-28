package com.example.oskartestapp.data.remote.dto

data class GetSignUpFlowResponse(
    val expires_at: String,
    val id: String,
    val issued_at: String,
    val organization_id: Any,
    val request_url: String,
    val state: String,
    val type: String,
    val ui: Ui
)