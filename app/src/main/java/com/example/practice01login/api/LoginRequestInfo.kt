package com.example.practice01login.api

import com.google.gson.annotations.SerializedName

data class LoginRequestInfo(
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?
)