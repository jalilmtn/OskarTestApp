package com.example.oskartestapp.data.remote

import com.example.oskartestapp.data.remote.dto.GetLoginFlowResponse
import com.example.oskartestapp.data.remote.dto.GetSignUpFlowResponse
import com.example.oskartestapp.data.remote.dto.RegistrationRequest
import com.example.oskartestapp.data.remote.dto.SignInRequest
import com.example.oskartestapp.data.remote.dto.SignInResponse
import com.example.oskartestapp.data.remote.dto.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OryApi {
    @GET("self-service/login/api")
    suspend fun getLoginFlow(): GetLoginFlowResponse

    @GET("self-service/registration/api")
    suspend fun getSignUpFlow(): GetSignUpFlowResponse


    @POST("self-service/login")
    suspend fun signIn(
        @Query("flow") flow: String,
        @Body signInRequest: SignInRequest
    ): SignInResponse

    @POST("self-service/registration")
    suspend fun signUp(
        @Query("flow") flow: String,
        @Body registrationRequest: RegistrationRequest
    ): SignUpResponse

}