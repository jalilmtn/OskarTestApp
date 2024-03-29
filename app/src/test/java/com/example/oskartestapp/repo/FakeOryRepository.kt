package com.example.oskartestapp.repo

import com.example.oskartestapp.data.remote.dto.GetLoginFlowResponse
import com.example.oskartestapp.data.remote.dto.GetSignUpFlowResponse
import com.example.oskartestapp.data.remote.dto.SignInResponse
import com.example.oskartestapp.data.remote.dto.SignUpResponse
import com.example.oskartestapp.domain.repo.OryRepository

class FakeOryRepository : OryRepository {
    override suspend fun getLoginFlow(): GetLoginFlowResponse {
       throw NotImplementedError()
    }

    override suspend fun getSignUpFlow(): GetSignUpFlowResponse {
        throw NotImplementedError()
    }

    override suspend fun signIn(flow: String, email: String, password: String): SignInResponse {
        throw NotImplementedError()
    }

    override suspend fun signUp(
        flow: String,
        email: String,
        password: String,
        token: String,
        firstName: String?,
        lastName: String?
    ): SignUpResponse {
        throw NotImplementedError()
    }
}