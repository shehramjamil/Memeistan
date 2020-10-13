package com.example.dagger_android.base

/*
This Class is used for building up the activity component
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memeistan.BaseApplication
import com.example.memeistan.di.components.ActivityComponent
import com.example.memeistan.di.components.ApplicationComponent
import com.example.memeistan.di.modules.ActivityModule

abstract class AbstractActivity : AppCompatActivity() {

    val activityComponent: ActivityComponent by lazy { setUpComponent() }
    protected abstract fun makeInjection(activityComponent: ActivityComponent)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeInjection(activityComponent)
    }

    private fun setUpComponent(): ActivityComponent {
        return getAppComponent().plus(ActivityModule(this))
    }

    private fun getAppComponent(): ApplicationComponent {
        return (application as BaseApplication).applicationComponent
    }


}