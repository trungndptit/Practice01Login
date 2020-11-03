package com.example.practice01login.api

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String,
    val user: User?) {

    data class User(
        val userId: Long,
        val userName: String)
}