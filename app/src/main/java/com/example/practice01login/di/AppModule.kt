package com.example.practice01login.di

import android.app.Application
import android.content.Context
import com.example.practice01login.MyApp
import com.example.practice01login.api.AppService
import com.example.practice01login.db.UserDatabase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: Application) = application


    @Provides
    fun provideGithubApi() = AppService.instance


//    @Provides
//    fun provideUserDao(context: Context) = UserDatabase.getInstance(context)
}