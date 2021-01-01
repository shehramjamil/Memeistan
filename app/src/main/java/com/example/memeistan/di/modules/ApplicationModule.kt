package com.example.memeistan.di.modules

import android.app.Application
import com.example.memeistan.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApplication) {

    @Singleton
    @Provides
    fun provideApplication(): Application = baseApp
}