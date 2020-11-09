package com.example.practice01login.api

import okhttp3.Headers

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String,
    val xAcc:String,
    val user: User?) {

    data class User(
        val userId: Long,
        val userName: String)
}