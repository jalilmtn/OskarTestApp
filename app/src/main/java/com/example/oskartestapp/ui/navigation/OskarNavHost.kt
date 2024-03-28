package com.example.oskartestapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.oskartestapp.ui.home.homeScreen
import com.example.oskartestapp.ui.home.navigateToHome
import com.example.oskartestapp.ui.signin.ROUTE_SIGN_IN
import com.example.oskartestapp.ui.signin.signInScreen
import com.example.oskartestapp.ui.signup.navigateToSignUp
import com.example.oskartestapp.ui.signup.signUpScreen

@Composable
fun OskarNavHost(modifier: Modifier, navController: NavHostController) {
    NavHost(modifier = modifier, navController = navController, startDestination = ROUTE_SIGN_IN) {
        signInScreen(
            goToSignUp =
            navController::navigateToSignUp,
            successLogin = navController::navigateToHome
        )
        signUpScreen(
            navController::navigateUp,
            successLogin = navController::navigateToHome
        )
        homeScreen()
    }
}