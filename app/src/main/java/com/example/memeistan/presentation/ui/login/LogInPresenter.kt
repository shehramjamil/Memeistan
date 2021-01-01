package com.example.memeistan.presentation.ui.login

import com.example.memeistan.data.model.json.LoginResponse
import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.business.usecases.GetLoginResponseUseCase
import com.example.memeistan.business.usecases.GetLoginStateUseCase
import com.example.memeistan.business.usecases.InsertLoginDataUseCase
import com.example.memeistan.presentation.base.BasePresenter
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LogInPresenter @Inject constructor(
    private val realmLoginMapper: RealmLoginMapper,
    private val getLoginStateUseCase: GetLoginStateUseCase,
    private val getLoginResponseUseCase: GetLoginResponseUseCase,
    private val insertLoginDataUseCase: InsertLoginDataUseCase
) :
    BasePresenter<LoginViewInterface>(), LoginPresenterInterface {
    val disposables = CompositeDisposable()

    override fun loginValidation(userName: String, password: String): String {
        return when {
            userName == "" -> "Username is empty"
            password == "" -> "Password is empty"
            else -> "Validation Passed"
        }
    }

    override fun checkLoginState() {
        getLoginStateUseCase
            .execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(LoginState())

    }

    override fun tryLoginOnServer(userName: String, password: String) {
        getLoginResponseUseCase
            .execute(userName, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loginResponseObserver())
    }

    inner class LoginState : Observer<LoginBusinessModel> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: LoginBusinessModel) {
            if (t.loginStatus) {
                view?.navigateToMainActivity()
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }

        override fun onComplete() {
        }

    }

    private fun loginResponseObserver(): Observer<LoginResponse> {
        return object : Observer<LoginResponse> {

            override fun onComplete() {
            }

            override fun onSubscribe(p0: Disposable) {
                //p0.dispose()
            }

            override fun onNext(response: LoginResponse) {
                if (response.response == "Login Successful") {
                    val mappedToRealmLoginModel =
                        realmLoginMapper.loginResponseToUserLoginRealmModel(response)
                    insertLoginDataUseCase.execute(mappedToRealmLoginModel)
                    view?.navigateToMainActivity()
                } else {
                    view?.loginServerStatus(response.response)
                }
            }

            override fun onError(p0: Throwable) {
                view?.loginServerStatus(p0.toString())

            }
        }
    }
}