package com.example.oskartestapp.data.remote.dto

data class Node(
    val attributes: Attributes,
    val group: String,
    val messages: List<Any>,
    val meta: Meta,
    val type: String
)