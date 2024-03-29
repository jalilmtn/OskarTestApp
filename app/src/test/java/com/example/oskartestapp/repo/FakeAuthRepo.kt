package com.example.oskartestapp.repo


import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAuthRepo : AuthRepo {
    private val authItems = mutableListOf<AuthItem>()

    override suspend fun addAuth(authItem: AuthItem) {
        authItems.add(authItem)
    }

    override suspend fun getAuth(key: String): AuthItem {
        return authItems.find { it.id == key } ?: throw Exception("Not found")
    }

    override fun getAuthFlow(key: String): Flow<AuthItem> {
        return flow {
            emit(authItems.find { it.id == key } ?: throw Exception("Not found"))
        }
    }
}