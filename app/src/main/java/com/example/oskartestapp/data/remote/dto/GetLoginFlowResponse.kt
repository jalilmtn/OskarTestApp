package com.example.oskartestapp.data.remote.dto

data class GetLoginFlowResponse(
    val created_at: String,
    val expires_at: String,
    val id: String,
    val issued_at: String,
    val organization_id: Any,
    val refresh: Boolean,
    val request_url: String,
    val requested_aal: String,
    val state: String,
    val type: String,
    val ui: Ui,
    val updated_at: String
)