package com.example.practice01login.di

import com.example.practice01login.ui.DiaryActivity
import com.example.practice01login.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule { // define những nơi được đối tượng trong module inject vào
    @ContributesAndroidInjector // render một đoạn code sẵn mà ở version trước phải làm tay trả về subcomponent
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector // render một đoạn code sẵn mà ở version trước phải làm tay trả về subcomponent
    abstract fun bindDiaryActivity() : DiaryActivity

}