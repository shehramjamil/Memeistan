package com.example.memeistan.business.usecases

import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.data.model.json.LoginResponse
import com.example.memeistan.data.model.realm.LoginRealmModel
import com.example.memeistan.data.provider.RealmLocalProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class InsertLoginDataUseCase @Inject constructor(private val realmLocalProvider: RealmLocalProvider) {
    fun execute(data: LoginRealmModel): Observable<Boolean> =
        realmLocalProvider.insertLoginData(data)


}