package com.example.memeistan.di.components

import com.example.memeistan.di.modules.ActivityModule
import com.example.memeistan.di.modules.FragmentModule
import com.example.memeistan.presentation.ui.login.LoginActivity
import com.example.memeistan.presentation.ui.signup.SignUpActivity
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun plus(fragmentModule: FragmentModule): FragmentComponent
}