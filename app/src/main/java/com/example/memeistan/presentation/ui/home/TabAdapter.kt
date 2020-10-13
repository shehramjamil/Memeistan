package com.example.memeistan.presentation.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.memeistan.presentation.ui.home.fresh_memes.FreshMemes
import com.example.memeistan.presentation.ui.home.top_memes.TopMemes

class TabAdapter(fm: FragmentManager, var totalTabs: Int) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FreshMemes()
            }
            else -> {
                TopMemes()
            }
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}