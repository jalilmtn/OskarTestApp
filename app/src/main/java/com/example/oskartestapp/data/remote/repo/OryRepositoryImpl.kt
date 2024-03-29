package com.example.oskartestapp.data.remote.repo

import com.example.oskartestapp.data.remote.OryApi
import com.example.oskartestapp.data.remote.dto.GetLoginFlowResponse
import com.example.oskartestapp.data.remote.dto.GetSignUpFlowResponse
import com.example.oskartestapp.data.remote.dto.Name
import com.example.oskartestapp.data.remote.dto.RegistrationRequest
import com.example.oskartestapp.data.remote.dto.SignInRequest
import com.example.oskartestapp.data.remote.dto.SignInResponse
import com.example.oskartestapp.data.remote.dto.SignUpResponse
import com.example.oskartestapp.data.remote.dto.Traits
import com.example.oskartestapp.domain.repo.OryRepository
import javax.inject.Inject

class OryRepositoryImpl @Inject constructor(
    private val api: OryApi,
) : OryRepository {
    override suspend fun getLoginFlow(): GetLoginFlowResponse {
        return api.getLoginFlow()
    }

    override suspend fun getSignUpFlow(): GetSignUpFlowResponse {
        return api.getSignUpFlow()
    }

    override suspend fun signIn(flow: String, email: String, password: String): SignInResponse {
        return api.signIn(flow, SignInRequest(identifier = email, password = password))
    }

    override suspend fun signUp(
        flow: String,
        email: String,
        password: String,
        token: String,
        firstName: String?,
        lastName: String?
    ): SignUpResponse {
        return api.signUp(
            flow,
            RegistrationRequest(
                csrf_token = token,
                traits = Traits(email = email, Name(first = firstName, last = lastName)),
                password = password
            )
        )
    }

}