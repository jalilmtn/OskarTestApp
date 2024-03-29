package com.example.oskartestapp.usecase


import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.domain.usecases.UserPassValidatorUseCase
import com.example.oskartestapp.repo.FakeAuthRepo
import com.example.oskartestapp.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckUserPassValidationUseCaseTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var userPassValidatorUseCase: UserPassValidatorUseCase

    @Before
    fun setUp() {

        userPassValidatorUseCase = UserPassValidatorUseCase()
    }

    @Test
    fun wrongEmail() {
        assertThat(
            userPassValidatorUseCase(
                "test",
                "test"
            ).message
        ).isEqualTo(ErrorResource.VALIDATION.message)
    }

    @Test
    fun wrongPassword() {
        assertThat(
            userPassValidatorUseCase(
                "test@test.com",
                "12"
            ).message
        ).isEqualTo(ErrorResource.VALIDATION.message)
    }

    @Test
    fun successState() {
        assertThat(
            userPassValidatorUseCase(
                "test@test.com",
                "1223222JJ"
            ).data
        ).isEqualTo(true)
    }


}