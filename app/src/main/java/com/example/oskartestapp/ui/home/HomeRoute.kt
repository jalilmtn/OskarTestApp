package com.example.oskartestapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.oskartestapp.common.clearAppData
import com.example.oskartestapp.common.findActivity
import com.example.oskartestapp.ui.signin.SignInViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val activity = LocalContext.current.findActivity()

    val state = homeViewModel.state.collectAsState().value
    LaunchedEffect(key1 = Unit, block = {
        homeViewModel.signal.collectLatest {
            if (it is HomeViewModel.Signal.Logout)
                activity.clearAppData()
        }
    })
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getAuthData()
    }

    HomeScreen(
        state
    )
}






