package com.example.oskartestapp.ui.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oskartestapp.common.Resource
import com.example.oskartestapp.di.IoDispatcher
import com.example.oskartestapp.di.MainDispatcher
import com.example.oskartestapp.domain.usecases.GetAndSaveLoginFlowUseCase
import com.example.oskartestapp.domain.usecases.GetAuthFlowUseCase
import com.example.oskartestapp.domain.usecases.GetAuthUseCase
import com.example.oskartestapp.domain.usecases.SignInUseCase
import com.example.oskartestapp.ui.signin.SignInViewModel
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
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthUseCase: GetAuthUseCase,
    private val getAuthFlowUseCase: GetAuthFlowUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    private val _signal = MutableSharedFlow<Signal>()
    val signal = _signal.asSharedFlow()

    fun getAuthData() {
        viewModelScope.launch {
            getAuthUseCase().collectLatest {
                when (it) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> _state.value = _state.value.copy(
                        email = it.data?.email ?: "",
                        name = it.data?.name ?: ""
                    )
                }

            }
        }

        viewModelScope.launch {
            getAuthFlowUseCase().collectLatest {
                if (it.isCredentialValid.not()) {
                    _signal.emit(Signal.Logout)
                }
            }
        }
    }

    sealed class Signal {
        data object Logout : Signal()
    }
}


@Stable
data class State(
    val email: String = "",
    val name: String = ""
)
