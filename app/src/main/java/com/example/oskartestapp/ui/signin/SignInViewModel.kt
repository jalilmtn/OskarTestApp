package com.example.oskartestapp.ui.signin

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.di.IoDispatcher
import com.example.oskartestapp.di.MainDispatcher
import com.example.oskartestapp.domain.usecases.GetAndSaveLoginFlowUseCase
import com.example.oskartestapp.domain.usecases.GetAuthUseCase
import com.example.oskartestapp.domain.usecases.SignInUseCase
import com.example.oskartestapp.ui.signup.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getAndSaveLoginFlowUseCase: GetAndSaveLoginFlowUseCase,
    private val signInUseCase: SignInUseCase,
    private val getAuthUseCase: GetAuthUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    private val _signal = MutableSharedFlow<Signal>()
    val signal = _signal.asSharedFlow()
    private val mutex = Mutex()

    fun changeEmail(mail: String) {
        _state.value = _state.value.copy(email = mail)
    }

    fun changePassword(pass: String) {
        _state.value = _state.value.copy(password = pass)
    }

    fun checkUserIsValid() {
        viewModelScope.launch {
            getAuthUseCase().collect {
                when (it) {
                    is Resource.Error -> Unit

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        if (it.data?.isCredentialValid == true)
                            _signal.emit(Signal.SuccessLogin)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun signIn() {
        viewModelScope.launch {
            mutex.withLock {
                getAndSaveLoginFlowUseCase().flatMapConcat {
                    when (it) {
                        is Resource.Success -> signInUseCase(
                            it.data?.id,
                            _state.value.email,
                            state.value.password
                        )

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                            emptyFlow()
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(isLoading = false)
                            _signal.emit(Signal.SnackBarMessage(it.message!!))
                            emptyFlow()
                        }
                    }
                }.collectLatest {
                    when (it) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(isLoading = false)
                            _signal.emit(Signal.SnackBarMessage(it.message!!))
                        }

                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _state.value = _state.value.copy(isLoading = false)
                            _signal.emit(Signal.SuccessLogin)
                        }
                    }
                }

            }
        }
    }


    sealed class Signal {
        data class SnackBarMessage(val message: String) : Signal()
        data object SuccessLogin : Signal()
    }
}


@Stable
data class State(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)
