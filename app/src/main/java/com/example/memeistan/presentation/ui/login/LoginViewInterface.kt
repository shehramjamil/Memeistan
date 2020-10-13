package com.example.memeistan.presentation.ui.login

import com.example.memeistan.presentation.base.BaseView

interface LoginViewInterface : BaseView {
    fun loginServerStatus(serverStatus: String)
    fun navigateToMainActivity()
}