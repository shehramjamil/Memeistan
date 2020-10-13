package com.example.dagger_android.base

/*
This Class is used for building up the fragment component
 */

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.memeistan.di.components.FragmentComponent
import com.example.memeistan.di.modules.FragmentModule

abstract class AbstractFragment : Fragment() {

    val activity by lazy { getActivity() as AbstractActivity }
    private val fragmentComponent: FragmentComponent by lazy { setUpComponent() }
    protected abstract fun makeInjection(fragmentComponent: FragmentComponent)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeInjection(fragmentComponent)
    }

    private fun setUpComponent(): FragmentComponent {
        return activity.activityComponent.plus(FragmentModule(this))
    }

}