package com.example.memeistan

import android.app.Application
import com.example.memeistan.di.components.ApplicationComponent
import com.example.memeistan.di.components.DaggerApplicationComponent
import com.example.memeistan.di.modules.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .name("Memeistan.realm")
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }


    private fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }
}