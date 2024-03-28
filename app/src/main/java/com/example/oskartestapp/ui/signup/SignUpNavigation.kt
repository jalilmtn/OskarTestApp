package com.example.oskartestapp.ui.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_SIGN_UP = "route_signup"


fun NavController.navigateToSignUp() {
    navigate(ROUTE_SIGN_UP)
}

fun NavGraphBuilder.signUpScreen(goToSignIn: () -> Unit,successLogin: () -> Unit) {

    composable(
        route = ROUTE_SIGN_UP
    ) {
        SignUpRoute(goToSignIn = goToSignIn, successLogin = successLogin)
    }
}


