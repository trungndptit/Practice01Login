package com.example.practice01login.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.practice01login.api.LoginRequestInfo
import com.example.practice01login.api.LoginResponse
import com.example.practice01login.db.UserEntity
import com.example.practice01login.repository.LoginRepo

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var loginRepo: LoginRepo? = null

    private fun loginResponseToUser(loginResponse: LoginResponse, xAcc: String): UserEntity {
        return UserEntity(
            loginResponse.user!!.userId,
            loginResponse.user.userName,
            xAcc
        )
    }

    fun doLogin(
        loginRequestInfo: LoginRequestInfo,
        callback: (UserEntity?, message : String) -> Unit
    ) {
        loginRepo?.doLogin(loginRequestInfo) { result, xAcc ->

            if (result == null || xAcc == null) {
                callback(null, "")
            } else {
                when(result.errorCode){
                    "00" -> {
                        callback(null, result.errorMessage)
                    }

                    "01" -> {
                        val user = loginResponseToUser(result, xAcc)
                        callback(user, result.errorMessage)
                        saveUser(user)
                    }
                }
            }
        }
    }

    private fun saveUser(userEntity: UserEntity) {
        val repo = loginRepo ?: return
        repo.saveUser(userEntity)

    }

    fun getUsers(callBack: (Int?) -> Unit) {
        val repo = loginRepo ?: return
        val users = repo.getAllUsers() {
            it?.let {
                callBack(it.size)
            }
        }
    }
}