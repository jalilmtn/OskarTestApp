package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.data.remote.dto.SignInResponse
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import com.example.oskartestapp.domain.repo.OryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val oryRepository: OryRepository,
    private val saveAuthUseCase: SaveAuthUseCase,
) {

    operator fun invoke(
        flow: String?,
        email: String,
        password: String
    ): Flow<Resource<SignInResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                if (flow == null) {
                    emit(Resource.Error(ErrorResource.ERROR))
                    return@flow
                }
                val signInResponse = oryRepository.signIn(
                    flow = flow,
                    email = email, password = password
                )
                var name: String? = null
                signInResponse.session.identity.traits.name?.let {
                    name = (it.first ?: "") + " " + (it.last ?: "")
                }
                saveAuthUseCase.invoke(
                    AuthItem(
                        id = KeyValueKeys.AUTH.name,
                        flow = flow,
                        email = signInResponse.session.identity.traits.email,
                        name = name,
                        token = signInResponse.session_token,
                        isCredentialValid = true
                    )
                ).collect {
                    when (it) {
                        is Resource.Error -> emit(Resource.Error(ErrorResource.ERROR))
                        is Resource.Loading -> Unit
                        is Resource.Success -> emit(Resource.Success(signInResponse))
                    }
                }

            } catch (e: HttpException) {
                //Exception for an unexpected, non-2xx HTTP response.
                emit(Resource.Error(ErrorResource.HTTP_ERROR))
            } catch (e: IOException) {
                emit(Resource.Error(ErrorResource.IO_ERROR))
                //cant talk with api
            }
        }

}