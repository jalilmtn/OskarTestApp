package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.data.remote.dto.GetLoginFlowResponse
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import com.example.oskartestapp.domain.repo.OryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAndSaveLoginFlowUseCase @Inject constructor(
    private val oryRepository: OryRepository,
    private val authRepo: AuthRepo
) {

    operator fun invoke(): Flow<Resource<GetLoginFlowResponse>> = flow {
        try {
            emit(Resource.Loading())
            val getLoginFlowResponse = oryRepository.getLoginFlow()
            authRepo.addAuth(
                AuthItem(
                    id = KeyValueKeys.AUTH.name,
                    flow = getLoginFlowResponse.id
                )
            )
            emit(Resource.Success(getLoginFlowResponse))
        } catch (e: HttpException) {
            //Exception for an unexpected, non-2xx HTTP response.
            emit(Resource.Error(ErrorResource.HTTP_ERROR))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorResource.IO_ERROR))
            //cant talk with api
        }
    }

}