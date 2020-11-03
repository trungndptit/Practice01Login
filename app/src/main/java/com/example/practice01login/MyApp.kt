package com.example.practice01login

import android.app.Application
import android.content.Context

class MyApp : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object{
        private var instance: MyApp? = null

        fun getInstance(): MyApp? {
            return instance
        }

        fun getContext(): Context? {
            return instance
        }

    }
}