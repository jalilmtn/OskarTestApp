package com.example.oskartestapp.ui.signup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpRoute(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    goToSignIn: () -> Unit,
    successLogin: () -> Unit
) {


    val state = signUpViewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = Unit, block = {
        signUpViewModel.signal.collectLatest {
            when (it) {
                is SignUpViewModel.Signal.SnackBarMessage -> snackBarHostState.showSnackbar(message = it.message)
                SignUpViewModel.Signal.SuccessLogin -> successLogin.invoke()
            }
        }
    })
    SignUpScreen(
        state,
        changeEmail = signUpViewModel::changeEmail,
        changePassword = signUpViewModel::changePassword,
        changeFirstName = signUpViewModel::changeFirstName,
        changeLastName = signUpViewModel::changeLastName,
        signUp = signUpViewModel::signUp,
        goToSignUp = goToSignIn,
        snackBarHostState = snackBarHostState
    )

}
