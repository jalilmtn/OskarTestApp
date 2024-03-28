package com.example.oskartestapp.data.local.repo

import com.example.oskartestapp.data.source.AuthDao
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDao: AuthDao
) : AuthRepo {
    override suspend fun addAuth(authItem: AuthItem) {
        authDao.addAuth(authItem)
    }

    override suspend fun getAuth(key: String): AuthItem {
        return authDao.getAuth(key)
    }

    override fun getAuthFlow(key: String): Flow<AuthItem> {
        return authDao.getAuthFlow(key)
    }

}