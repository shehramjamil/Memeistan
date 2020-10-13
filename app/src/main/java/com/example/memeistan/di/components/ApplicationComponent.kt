package com.example.memeistan.di.components

import com.example.dagger_android.di.modules.ApiModule
import com.example.memeistan.BaseApplication
import com.example.memeistan.di.modules.ActivityModule
import com.example.memeistan.di.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {

    fun inject(application: BaseApplication)
    fun plus(activityModule: ActivityModule): ActivityComponent  //  For using Activity Module Along with Application Module

}