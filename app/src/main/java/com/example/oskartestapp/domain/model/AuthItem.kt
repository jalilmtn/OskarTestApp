package com.example.oskartestapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthItem(
    @PrimaryKey
    val id: String,
    val flow: String,
    val email: String? = null,
    val name: String? = null,
    val token: String? = null,
    val isCredentialValid: Boolean = false
)