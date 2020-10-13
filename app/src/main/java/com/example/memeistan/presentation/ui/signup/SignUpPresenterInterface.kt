package com.example.memeistan.presentation.ui.signup

import android.net.Uri

interface SignUpPresenterInterface {
    fun signUpValidation(
        userName: String,
        password: String,
        repeatPassword: String,
        gender: String,
        imageString: String
    ): String

    fun openGallery()
    fun compressAndConvertImageToString(uri: Uri?): String
    fun trySignUpOnServer(userName: String, password: String, gender: String, imageString: String)
}