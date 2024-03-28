package com.example.oskartestapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmallestSpacer(){
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
fun SmallSpacer(){
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun MediumSpacer(){
    Spacer(modifier = Modifier.size(16.dp))
}
@Composable
fun LargeSpacer(){
    Spacer(modifier = Modifier.size(24.dp))
}

@Composable
fun ExtraLargeSpacer(){
    Spacer(modifier = Modifier.size(32.dp))
}
