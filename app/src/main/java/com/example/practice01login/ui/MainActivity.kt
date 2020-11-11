package com.example.practice01login.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.practice01login.MyApp
import com.example.practice01login.R
import com.example.practice01login.api.AppService
import com.example.practice01login.api.LoginRequestInfo
import com.example.practice01login.db.UserDatabase
import com.example.practice01login.db.UserEntity
import com.example.practice01login.repository.AppRepo
import com.example.practice01login.utils.DateUtils
import com.example.practice01login.viewmodel.LoginViewModel
import com.facebook.CallbackManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    lateinit var callbackManager: CallbackManager

    @Inject
    lateinit var appService: AppService

    @Inject
    lateinit var context: MyApp

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)  // Dagger biết cái activity này đc binding
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create();
        var date : String = DateUtils.jsonDateToShortDate("2020-04-28T00:03:22.303Z")
        var date1 : Date = DateUtils.xmlDateToDate("2020-04-28T00:03:22.303Z")
        var pattern = "MM/dd/yyyy"
        val df: DateFormat = SimpleDateFormat(pattern)
        val dayAsString = df.format(date1)
        println("Debug date : $date and date : $date1 and string : $dayAsString")

        setContentView(R.layout.activity_main)
        setupViewModels()
        setupInputKeyboard()
//        createUserObserver()

    }

    private fun setupInputKeyboard() {

        til_username
        et_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()) {
                    til_username.error = null
                }
            }

        })

        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()) {
                    til_password.error = null
                }
            }

        })

        btn_login.setOnClickListener {

            if (et_username.text.toString().isBlank() || et_password.text.toString().isBlank()) {
                if (et_username.text.toString().isBlank()) {
                    til_username.error = getString(R.string.message_empty)
                }
                if (et_password.text.toString().isBlank()) {
                    til_password.error = getString(R.string.message_empty)
                }
            } else {
                val username = et_username.text.toString()
                val password = et_password.text.toString()
                val login =
                    LoginRequestInfo(
                        username,
                        password
                    )
//                loginViewModel.doLoginRx(login)
//                loginViewModel.getListDiary()
                var intent = Intent(this, DiaryActivity::class.java)
                startActivity(intent)
//                showProgressBar()
            }
        }


//        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
//        LoginManager.getInstance()
//            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult?) {
//                    var profile: Profile = Profile.getCurrentProfile()
//                    println("Debug login fb token ${result?.accessToken?.token} and ${result?.accessToken?.userId}" +
//                            " and name ${profile.name} and uri ${profile.getProfilePictureUri(200,200)}")
//                }
//
//                override fun onCancel() {
//                    println("Debug login fb onCancel")
//                }
//
//                override fun onError(error: FacebookException?) {
//                    println("Debug login fb onError ${error.toString()}")
//                }
//
//            })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupViewModels() {
        val db = UserDatabase.getInstance(this)
        val userDao = db.userDao()
        loginViewModel.appRepo =
            AppRepo(appService, userDao)

    }

    private fun createUserObserver() {
        loginViewModel.getUserView()?.observe(this, Observer<UserEntity> {
            println("Debug livedata $it")
            if (it != null) {
                hideProgressBar()
            }
        })

        loginViewModel.noInternetConnectionEvent?.observe(this, Observer {
            println("Debug error : noInternetConnectionEvent")
            Toast.makeText(this, "No Enternet", Toast.LENGTH_SHORT).show()
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}