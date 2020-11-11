package com.example.practice01login.repository

import com.example.practice01login.api.AppService
import com.example.practice01login.api.DiaryResponse
import com.example.practice01login.db.UserDao
import com.example.practice01login.db.UserEntity
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppRepo(private val appService: AppService, private var userDao: UserDao) {

    companion object {
        const val XACC_STRING = "xAcc-Code"
    }

    fun getListDiary() : Observable<List<DiaryResponse>> = appService.getListDiaryRx()

//    fun doLogin(
//        loginRequestInfo: LoginRequestInfo,
//        callback: (LoginResponse?, String?) -> Unit
//    ) {
//        appService.doLogin(loginRequestInfo).enqueue(object : Callback<LoginResponse> {
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                callback(null, null)
//            }
//
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.body() != null && response.headers()
//                        .get("X-Acc") != null
//                ) {
//                    println("Debug ${response.headers().get("X-Acc")}")
//                    println("Debug ${response.body()}")
//                    callback(response.body(), response.headers().get("X-Acc"))
//
//                    val sharedPreferences =
//                        PreferenceManager.getDefaultSharedPreferences(MyApp.getContext()).edit()
//                    sharedPreferences.putString(XACC_STRING, response.headers().get("X-Acc"))
//                    sharedPreferences.apply()
//                } else
//                    callback(null, null)
//            }
//
//        })
//    }

//    fun doLoginRx(loginRequestInfo: LoginRequestInfo) : Observable<LoginResponse>{
//        return appService.doLoginRx(loginRequestInfo)
//            .doAfterNext { response ->
//                val sharedPreferences =
//                    PreferenceManager.getDefaultSharedPreferences(MyApp.getContext()).edit()
//                sharedPreferences.putString(XACC_STRING, response.headers().get("X-Acc"))
//                sharedPreferences.apply()
//            }
//            .map { response ->
//            LoginResponse(response.body()!!.errorCode, response.body()!!.errorMessage,
//                response.headers().get("X-Acc")!!, response.body()!!.user)
//        }
//    }

    fun saveUser(userEntity: UserEntity) {
        GlobalScope.launch {
            val userId =
                userDao.insertUser(userEntity) // insertUser give back a long value. it's userId = primary key
            println("Debug userId $userId")
        }
    }

    fun getAllUsers(callback: (List<UserEntity>?) -> Unit) {
        GlobalScope.launch {
            val users = userDao.loadAllUsers()
            GlobalScope.launch(Dispatchers.Main) {
                callback(users)
            }
        }
    }
}