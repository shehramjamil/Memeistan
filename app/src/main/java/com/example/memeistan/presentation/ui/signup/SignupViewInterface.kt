package com.example.memeistan.presentation.ui.signup

import com.example.memeistan.presentation.base.BaseView

interface SignupViewInterface : BaseView {

    fun signUpValidationStatus(validationStatus: String)
    fun signUpServerStatus(serverStatus: String)

}