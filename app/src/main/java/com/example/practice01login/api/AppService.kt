package com.example.practice01login.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface AppService {

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    fun doLogin(@Body loginRequestInfo: LoginRequestInfo): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    fun doLoginRx(@Body loginRequestInfo: LoginRequestInfo): Observable<Response<LoginResponse>>

    @GET("/notes")
    fun getListDiaryRx(): Observable<List<DiaryResponse>>

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
                .baseUrl("https://private-ba0842-gary23.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            retrofit.create<AppService>(
                AppService::class.java)
        }
    }
}