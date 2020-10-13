package com.example.memeistan.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.memeistan.R
import com.example.memeistan.presentation.base.AbstractMvpActivity
import com.example.memeistan.di.components.ActivityComponent
import com.example.memeistan.presentation.ui.home.MainActivity
import com.example.memeistan.presentation.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class LoginActivity : AbstractMvpActivity<LoginViewInterface, LogInPresenter>(),
    LoginViewInterface {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        presenter.checkLoginState()

        signUp.setOnClickListener {
            navigateToSignUpActivity()
        }
        logIn.setOnClickListener {
            val validationStatus =
                presenter.loginValidation(userName.text.toString(), password.text.toString())
            if (validationStatus == "Validation Passed") {
                presenter.tryLoginOnServer(userName.text.toString(), password.text.toString())
            } else {
                Toast.makeText(this, validationStatus, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun loginServerStatus(serverStatus: String) {
        runOnUiThread {
            Toast.makeText(this, serverStatus, Toast.LENGTH_SHORT).show()
        }
    }

    override fun makeInjection(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    private fun navigateToSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}