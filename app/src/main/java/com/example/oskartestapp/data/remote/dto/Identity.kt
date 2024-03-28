package com.example.oskartestapp.data.remote.dto

data class Identity(
    val created_at: String,
    val id: String,
    val metadata_public: Any,
    val organization_id: Any,
    val recovery_addresses: List<RecoveryAddresse>,
    val schema_id: String,
    val schema_url: String,
    val state: String,
    val state_changed_at: String,
    val traits: Traits,
    val updated_at: String,
    val verifiable_addresses: List<VerifiableAddresse>
)