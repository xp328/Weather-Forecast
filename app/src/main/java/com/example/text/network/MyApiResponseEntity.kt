package com.example.text.network

data class MyApiResponseEntity<T>(
    val code: Int,
    val msg: String,
    val data: T
)