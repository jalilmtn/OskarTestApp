package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.data.remote.dto.SignUpResponse
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.OryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val oryRepository: OryRepository,
    private val saveAuthUseCase: SaveAuthUseCase,
) {

    operator fun invoke(
        flow: String,
        email: String,
        password: String,
        token: String,
        firstName: String?,
        lastName: String?
    ): Flow<Resource<SignUpResponse>> =
        flow {
            try {
                emit(Resource.Loading())
                val signInResponse =
                    oryRepository.signUp(
                        flow = flow,
                        email = email,
                        password = password,
                        token = token,
                        firstName = firstName,
                        lastName = lastName
                    )
                var name: String? = null
                signInResponse.session.identity.traits.name?.let {
                    name = (it.first ?: "") + " " + (it.last ?: "")
                }
                saveAuthUseCase.invoke(
                    AuthItem(
                        id = KeyValueKeys.AUTH.name,
                        flow = flow,
                        name = name,
                        email = signInResponse.session.identity.traits.email,
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
            } catch (e: Exception) {
                emit(Resource.Error(ErrorResource.ERROR))
            }
        }

}