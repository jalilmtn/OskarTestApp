package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthFlowUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    operator fun invoke(): Flow<AuthItem> = authRepo.getAuthFlow(KeyValueKeys.AUTH.name)
}