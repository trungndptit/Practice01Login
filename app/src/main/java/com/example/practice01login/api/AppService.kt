package com.example.practice01login.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface AppService {

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    fun doLogin(@Body loginRequestInfo: LoginRequestInfo): Call<LoginResponse>

    companion object {
        var logging = HttpLoggingInterceptor().apply {
            this.level=HttpLoggingInterceptor.Level.HEADERS
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(SupportInterceptor())
            .addInterceptor(logging)
            .build()

        val instance: AppService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://private-222d3-homework5.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            retrofit.create<AppService>(
                AppService::class.java)
        }
    }
}