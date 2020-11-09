package com.example.practice01login

import android.content.Context
import com.example.practice01login.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApp : DaggerApplication() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build()


    companion object {
        private var instance: MyApp? = null

        fun getInstance(): MyApp? {
            return instance
        }

        fun getContext(): Context? {
            return instance
        }

    }
}