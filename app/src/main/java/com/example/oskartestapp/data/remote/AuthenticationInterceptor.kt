package com.example.oskartestapp.data.remote

import com.example.oskartestapp.common.KeyValueKeys
import com.example.oskartestapp.domain.model.AuthItem
import com.example.oskartestapp.domain.repo.AuthRepo
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class AuthenticationInterceptor @Inject constructor(
    private val authRepo: AuthRepo,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()

        try {
            val token = authRepo.getAuth(KeyValueKeys.AUTH.name).token
            token?.let {
                request.addAuthentication(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val response = chain.proceed(request)

//        There is no good way to refresh token right now in ory api
//        user needs to login again
        if (response.code == 401) {
            authRepo.addAuth(
                AuthItem(
                    id = KeyValueKeys.AUTH.name,
                    flow = "",
                    isCredentialValid = false
                )
            )
        }
        response

    }

    private fun Request.addAuthentication(
        token: String
    ): Request = newBuilder()
        .addHeader(
            "Authorization",
            "Bearer $token"
        )
        .build()
}
