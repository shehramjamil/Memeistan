package com.example.memeistan.presentation.ui.login

interface LoginPresenterInterface {
    fun loginValidation(userName: String, password: String): String
    fun checkLoginState()
    fun tryLoginOnServer(userName: String, password: String)
}