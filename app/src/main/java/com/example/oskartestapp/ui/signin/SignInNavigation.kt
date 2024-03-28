package com.example.oskartestapp.ui.signin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_SIGN_IN = "notes_sign_in"

fun NavGraphBuilder.signInScreen(goToSignUp: () -> Unit, successLogin: () -> Unit) {
    composable(
        route = ROUTE_SIGN_IN
    ) {
        SignInRoute( goToSignUp = goToSignUp, successLogin = successLogin)
    }
}