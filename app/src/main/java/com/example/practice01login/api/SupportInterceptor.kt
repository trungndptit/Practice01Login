package com.example.practice01login.api

import androidx.preference.PreferenceManager
import com.example.practice01login.MyApp
import com.example.practice01login.repository.AppRepo
import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(MyApp.getContext())

        val xAcc = sharedPreferences.getString(AppRepo.XACC_STRING, "")!!

        var request = chain.request()
        request = request.newBuilder()
            .addHeader("X-Acc", xAcc)
            .build()

        return chain.proceed(request)
    }
}