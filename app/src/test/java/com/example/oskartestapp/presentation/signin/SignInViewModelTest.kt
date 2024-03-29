package com.example.oskartestapp.presentation.signin

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.oskartestapp.domain.usecases.GetAndSaveLoginFlowUseCase
import com.example.oskartestapp.domain.usecases.GetAuthUseCase
import com.example.oskartestapp.domain.usecases.SaveAuthUseCase
import com.example.oskartestapp.domain.usecases.SignInUseCase
import com.example.oskartestapp.domain.usecases.UserPassValidatorUseCase
import com.example.oskartestapp.repo.FakeAuthRepo
import com.example.oskartestapp.repo.FakeOryRepository
import com.example.oskartestapp.ui.signin.SignInViewModel
import com.example.oskartestapp.utils.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var fakeAuthRepo: FakeAuthRepo
    private lateinit var fakeOryRepository: FakeOryRepository
    private lateinit var getAndSaveLoginFlowUseCase: GetAndSaveLoginFlowUseCase
    private lateinit var signInUseCase: SignInUseCase
    private lateinit var getAuthUseCase: GetAuthUseCase
    private lateinit var userPassValidatorUseCase: UserPassValidatorUseCase
    private lateinit var saveAuthUseCase: SaveAuthUseCase


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        fakeAuthRepo = FakeAuthRepo()
        fakeOryRepository = FakeOryRepository()
        getAndSaveLoginFlowUseCase = GetAndSaveLoginFlowUseCase(
            fakeOryRepository,
            fakeAuthRepo,
            UnconfinedTestDispatcher()
        )

        saveAuthUseCase = SaveAuthUseCase(
            fakeAuthRepo,
            UnconfinedTestDispatcher()
        )
        signInUseCase = SignInUseCase(
            fakeOryRepository,
            saveAuthUseCase,
            UnconfinedTestDispatcher()
        )
        getAuthUseCase = GetAuthUseCase(fakeAuthRepo, UnconfinedTestDispatcher())
        userPassValidatorUseCase = UserPassValidatorUseCase()
    }

    @Test
    fun changeEmail() = runBlocking {
        val testMail = "test@test.com"
        val viewModel = provideViewModel()
        viewModel.changeEmail(testMail)
        assertThat( viewModel.state.value.email).isEqualTo(testMail)
    }

    // TODO: this tests should be completed

    private fun provideViewModel(): SignInViewModel = SignInViewModel(
        getAndSaveLoginFlowUseCase,
        signInUseCase,
        getAuthUseCase,
        userPassValidatorUseCase
    )

}