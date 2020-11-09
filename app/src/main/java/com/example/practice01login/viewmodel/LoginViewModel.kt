package com.example.practice01login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practice01login.SingleLiveEvent
import com.example.practice01login.api.LoginRequestInfo
import com.example.practice01login.api.LoginResponse
import com.example.practice01login.db.UserEntity
import com.example.practice01login.repository.LoginRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var loginRepo: LoginRepo? = null
    var compositeDisposable = CompositeDisposable()

    private var user: MutableLiveData<UserEntity>? = MutableLiveData()

    val noInternetConnectionEvent = SingleLiveEvent<Unit>()
    val connectTimeoutEvent = SingleLiveEvent<Unit>()


    private fun loginResponseToUser(loginResponse: LoginResponse): MutableLiveData<UserEntity>? {
        val userEntity = UserEntity(
            loginResponse.user!!.userId,
            loginResponse.user.userName,
            loginResponse.xAcc
        )
        user!!.value = userEntity
        return this.user!!
    }

//    fun doLogin(
//        loginRequestInfo: LoginRequestInfo,
//        callback: (UserEntity?, message : String) -> Unit
//    ) {
//        loginRepo?.doLogin(loginRequestInfo) { result, xAcc ->
//
//            if (result == null || xAcc == null) {
//                callback(null, "")
//            } else {
//                when(result.errorCode){
//                    "00" -> {
//                        callback(null, result.errorMessage)
//                    }
//
//                    "01" -> {
//                        val user = loginResponseToUser(result, xAcc)
//                        callback(user, result.errorMessage)
//                        saveUser(user)
//                    }
//                }
//            }
//        }
//    }

    fun doLoginRx(loginRequestInfo: LoginRequestInfo) {
        val repo = loginRepo ?: return


        val disposable = repo.doLoginRx(loginRequestInfo)
//            .doOnError { error: Throwable -> println("Debug The error message is: " + error.message) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loginResponse ->

                user = loginResponseToUser(loginResponse)
                println("Debug rx onSuccess $loginResponse")
            }, {
                onLoadFail(it)
            })

        compositeDisposable.add(disposable)
    }

    fun getUserView(): LiveData<UserEntity>? {
        return this.user
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

    open fun onLoadFail(throwable: Throwable) {
        when (throwable.cause) {
            is UnknownHostException -> {
                noInternetConnectionEvent.value = null
            }
            is SocketTimeoutException -> {
                connectTimeoutEvent.value = null
            }
        }
    }

}