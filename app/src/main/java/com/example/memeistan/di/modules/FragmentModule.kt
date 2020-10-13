package com.example.memeistan.di.modules

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(val fragment: Fragment) {

    @Provides
    fun fragment(): Fragment = fragment


}