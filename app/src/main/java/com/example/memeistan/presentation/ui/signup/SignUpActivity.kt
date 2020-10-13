package com.example.memeistan.presentation.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.memeistan.R
import com.example.memeistan.REQUEST_CODE
import com.example.memeistan.presentation.base.AbstractMvpActivity
import com.example.memeistan.di.components.ActivityComponent
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AbstractMvpActivity<SignupViewInterface, SignUpPresenter>(),
    SignupViewInterface {

    var imageString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        circularImage.setOnClickListener {
            presenter.openGallery()
        }
        signUp.setOnClickListener {
            val validationStatus = presenter.signUpValidation(
                userName.text.toString(),
                password.text.toString(),
                repeatPassword.text.toString(),
                gender.text.toString(),
                imageString
            )
            when (validationStatus) {
                "Validation Passed" -> presenter.trySignUpOnServer(
                    userName.text.toString(),
                    password.text.toString(),
                    gender.text.toString(),
                    imageString
                )
                else -> {
                    Toast.makeText(this, validationStatus, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun signUpValidationStatus(validationStatus: String) {
        Toast.makeText(this, validationStatus, Toast.LENGTH_SHORT).show()
    }

    override fun signUpServerStatus(serverStatus: String) {
        runOnUiThread {
            Toast.makeText(this, serverStatus, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            circularImage.setImageURI(data?.data)
            imageString = presenter.compressAndConvertImageToString(data?.data)
        }
    }

    override fun makeInjection(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }
}