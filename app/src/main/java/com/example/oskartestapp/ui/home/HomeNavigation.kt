package com.example.oskartestapp.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_HOME= "route_home"

fun NavController.navigateToHome() {
    navigate(ROUTE_HOME)
}


fun NavGraphBuilder.homeScreen() {
    composable(
        route = ROUTE_HOME
    ) {
        HomeRoute()
    }
}