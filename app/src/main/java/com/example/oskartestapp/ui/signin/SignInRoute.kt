package com.example.oskartestapp.ui.signin

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInRoute(
    signInViewModel: SignInViewModel = hiltViewModel(),
    goToSignUp: () -> Unit,
    successLogin: () -> Unit,
) {

    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = Unit, block = {
        signInViewModel.checkUserIsValid()
        signInViewModel.signal.collectLatest {
            when (it) {
                is SignInViewModel.Signal.SnackBarMessage -> snackBarHostState.showSnackbar(message = it.message)
                SignInViewModel.Signal.SuccessLogin -> successLogin.invoke()
            }
        }
    })
    val state = signInViewModel.state.collectAsState().value

    SignInScreen(
        state,
        changeEmail = signInViewModel::changeEmail,
        changePassword = signInViewModel::changePassword,
        signIn = signInViewModel::signIn,
        goToSignUp = goToSignUp,
        snackBarHostState = snackBarHostState,
    )
}




