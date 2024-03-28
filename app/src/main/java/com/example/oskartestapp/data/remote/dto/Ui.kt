package com.example.oskartestapp.data.remote.dto

data class Ui(
    val action: String,
    val method: String,
    val nodes: List<Node>
)