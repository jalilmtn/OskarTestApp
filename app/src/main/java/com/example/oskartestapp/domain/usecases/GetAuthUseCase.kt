package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.di.IoDispatcher
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAuthUseCase @Inject constructor(
    private val authRepo: AuthRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<Resource<AuthItem>> = flow {
        try {
            emit(Resource.Loading())
            val auth =  withContext(ioDispatcher){
                authRepo.getAuth(KeyValueKeys.AUTH.name)

            }
            emit(Resource.Success(auth))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorResource.ERROR))
        }
    }

}