package com.example.oskartestapp.domain.repo

import com.example.oskartestapp.data.remote.dto.GetLoginFlowResponse
import com.example.oskartestapp.data.remote.dto.GetSignUpFlowResponse
import com.example.oskartestapp.data.remote.dto.SignInResponse
import com.example.oskartestapp.data.remote.dto.SignUpResponse


interface OryRepository {
    suspend fun getLoginFlow(): GetLoginFlowResponse
    suspend fun getSignUpFlow(): GetSignUpFlowResponse
    suspend fun signIn(flow: String, email: String, password: String): SignInResponse
    suspend fun signUp(
        flow: String,
        email: String,
        password: String,
        token: String
    ): SignUpResponse
}