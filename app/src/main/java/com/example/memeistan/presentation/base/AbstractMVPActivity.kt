package com.example.memeistan.presentation.base

import android.os.Bundle
import com.example.dagger_android.base.AbstractActivity
import javax.inject.Inject

abstract class AbstractMvpActivity<V : BaseView, P : BasePresenter<in V>> : AbstractActivity() {
    //region Variables
    @Inject
    lateinit var presenter: P

    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
