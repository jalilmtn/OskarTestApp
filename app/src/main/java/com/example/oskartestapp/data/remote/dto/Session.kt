package com.example.oskartestapp.data.remote.dto

data class Session(
    val active: Boolean,
    val authenticated_at: String,
    val authentication_methods: List<AuthenticationMethod>,
    val authenticator_assurance_level: String,
    val devices: List<Device>,
    val expires_at: String,
    val id: String,
    val identity: Identity,
    val issued_at: String
)