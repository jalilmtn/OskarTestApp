package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveAuthUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {

    operator fun invoke(authItem: AuthItem): Flow<Resource<AuthItem>> = flow {
        try {
            emit(Resource.Loading())
            authRepo.addAuth(authItem)
            emit(Resource.Success(authItem))
        } catch (e: Exception) {
            //Exception for an unexpected, non-2xx HTTP response.
            emit(Resource.Error(ErrorResource.ERROR))
        }
    }

}