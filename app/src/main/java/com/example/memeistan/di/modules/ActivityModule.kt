package com.example.memeistan.di.modules

import android.app.Activity
import android.content.Context
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import com.example.memeistan.data.provider.RealmLocalProvider
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity = activity

    @Provides
    fun provideContext(): Context = activity


    @Provides
    fun provideRealmLocalProvider(): RealmLoginMapper = RealmLoginMapper()

}