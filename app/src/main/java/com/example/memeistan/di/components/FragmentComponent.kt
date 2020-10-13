package com.example.memeistan.di.components

import com.example.memeistan.di.modules.FragmentModule
import com.example.memeistan.presentation.ui.home.fresh_memes.FreshMemes
import com.example.memeistan.presentation.ui.home.top_memes.TopMemes
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(freshMemes: FreshMemes)
    fun inject(topMemes: TopMemes)
}