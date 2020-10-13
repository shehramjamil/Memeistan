package com.example.memeistan

import android.app.Application
import com.example.memeistan.di.components.ApplicationComponent
import com.example.memeistan.di.components.DaggerApplicationComponent
import com.example.memeistan.di.modules.ApplicationModule
import com.facebook.stetho.Stetho
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

    private fun initStetho() {
        /*Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build())*/
    }


    fun setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }
}