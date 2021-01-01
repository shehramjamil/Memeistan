package com.example.memeistan.business.provider

import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.data.model.realm.LoginRealmModel
import io.reactivex.rxjava3.core.Observable


interface LocalProvider {
    fun insertLoginData(loginData: LoginRealmModel): Observable<Boolean>
    fun readLoggedUserData(): Observable<LoginBusinessModel>
    fun deleteLoginData(): Observable<Boolean>
}