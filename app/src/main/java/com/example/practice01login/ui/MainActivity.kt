package com.example.practice01login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practice01login.api.LoginRequestInfo
import com.example.practice01login.R
import com.example.practice01login.api.AppService
import com.example.practice01login.db.UserDatabase
import com.example.practice01login.repository.LoginRepo
import com.example.practice01login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModels()
        setupInputKeyboard()

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
                doLogin(login)
            }

        }
    }

    private fun setupViewModels() {
        val service = AppService.instance
        val db = UserDatabase.getInstance(this)
        val userDao = db.userDao()
        loginViewModel.loginRepo =
            LoginRepo(service, userDao)

    }

    private fun doLogin(loginRequestInfo: LoginRequestInfo) {
        showProgressBar()
        loginViewModel.doLogin(loginRequestInfo) { user, message ->
            hideProgressBar()
            if (user == null) {
                if (message == "") { // check if response in LoginViewModel callback null
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                } else { // check if response in LoginViewModel call back with error message 00 meaning password incorrect
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "UserId = ${user.userId} and Username = ${user.username}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}