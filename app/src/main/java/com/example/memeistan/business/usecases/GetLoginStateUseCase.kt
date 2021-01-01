package com.example.memeistan.business.usecases

import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.data.provider.RealmLocalProvider
import io.reactivex.rxjava3.core.Observable

import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val realmLocalProvider: RealmLocalProvider
) {

    fun execute(): Observable<LoginBusinessModel>
    {
        return realmLocalProvider.readLoggedUserData()
    }

}