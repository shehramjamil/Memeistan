package com.example.memeistan.presentation.ui.login

import android.content.Context
import com.example.memeistan.data.model.json.LoginResponse
import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.presentation.base.BasePresenter
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import com.example.memeistan.data.provider.RealmLocalProvider
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LogInPresenter @Inject constructor(
    var context: Context,
    private val retrofitInterface: RetrofitInterface,
    private val realmLocalProvider: RealmLocalProvider,
    private val realmLoginMapper: RealmLoginMapper
) :
    BasePresenter<LoginViewInterface>(), LoginPresenterInterface {


    override fun loginValidation(userName: String, password: String): String {
        return when {
            userName == "" -> "Username is empty"
            password == "" -> "Password is empty"
            else -> "Validation Passed"
        }
    }

    override fun checkLoginState() {
        realmLocalProvider.readLoggedUserData().observeOn(Schedulers.io())
            .subscribe(LoginState())
    }

    override fun tryLoginOnServer(userName: String, password: String) {
        val retrofitCall = retrofitInterface.getLoginResponse(userName, password)
        retrofitCall.subscribeOn(Schedulers.io()).subscribe(observer())
    }

    inner class LoginState : Observer<LoginBusinessModel> {
        override fun onSubscribe(d: Disposable) {
            TODO("Not yet implemented")
        }

        override fun onNext(t: LoginBusinessModel) {
            if (t.loginStatus) {
                view?.navigateToMainActivity()
            }
        }

        override fun onError(e: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

    }


    private fun loginStateObserver(): Observer<LoginBusinessModel> {
        return object : Observer<LoginBusinessModel> {

            override fun onComplete() {
            }

            override fun onSubscribe(p0: Disposable) {
                //p0.dispose()
            }

            override fun onNext(response: LoginBusinessModel) {
                if (response.loginStatus) {
                    view?.navigateToMainActivity()
                }
            }

            override fun onError(p0: Throwable) {
            }
        }
    }

    private fun observer(): Observer<LoginResponse> {
        return object : Observer<LoginResponse> {

            override fun onComplete() {
            }

            override fun onSubscribe(p0: Disposable) {
                //p0.dispose()
            }

            override fun onNext(response: LoginResponse) {
                if (response.response == "Login Successful") {
                    realmLocalProvider.insertLoginData(
                        realmLoginMapper.loginResponseToUserLoginRealmModel(
                            response
                        )
                    )
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