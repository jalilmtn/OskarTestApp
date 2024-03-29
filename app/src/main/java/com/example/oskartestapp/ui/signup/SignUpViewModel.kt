package com.example.oskartestapp.ui.signup

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.domain.usecases.GetAndSaveSignUpFlowUseCase
import com.example.oskartestapp.domain.usecases.SignUpUseCase
import com.example.oskartestapp.domain.usecases.UserPassValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
class SignUpViewModel @Inject constructor(
    private val getAndSaveSignUpFlowUseCase: GetAndSaveSignUpFlowUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val userPassValidatorUseCase: UserPassValidatorUseCase,
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


    @OptIn(ExperimentalCoroutinesApi::class)
    fun signUp() {
        viewModelScope.launch {
            mutex.withLock {
                when (val response = userPassValidatorUseCase.invoke(
                    _state.value.email,
                    state.value.password
                )) {
                    is Resource.Error -> _signal.emit(
                        Signal.SnackBarMessage(
                            response.message!!
                        )
                    )

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        getAndSaveSignUpFlowUseCase().flatMapConcat {
                            when (it) {
                                is Resource.Success -> signUpUseCase(
                                    flow = it.data!!.id,
                                    email = _state.value.email,
                                    password = state.value.password,
                                    token = it.data.ui.nodes[0].attributes.value
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
