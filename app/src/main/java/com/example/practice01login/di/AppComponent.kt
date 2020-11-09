package com.example.practice01login.di

import com.example.practice01login.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,  // cung cấp đối tượng mà chứa tất cả những nơi có thể injectable được kiểu Map
        AppModule::class,
        ActivityBindingModule::class]
)
interface AppComponent : AndroidInjector<MyApp>{ // android injector chứa những thành phần support như activity, fragment, service ...

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MyApp): Builder // đẩy tham số để xây dựng thằng AppComponent

        fun build(): AppComponent

    }
}