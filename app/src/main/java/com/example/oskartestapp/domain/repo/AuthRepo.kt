package com.example.oskartestapp.domain.repo

import com.example.oskartestapp.domain.model.AuthItem
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun addAuth(authItem: AuthItem)
    suspend fun getAuth(key: String): AuthItem
    fun getAuthFlow(key: String): Flow<AuthItem>
}