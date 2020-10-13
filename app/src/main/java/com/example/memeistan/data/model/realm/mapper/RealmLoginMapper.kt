package com.example.memeistan.data.model.realm.mapper

/*
    This class maps the login response coming from server to the Realm object for saving in database
 */

import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.data.model.realm.LoginRealmModel
import com.example.memeistan.data.model.json.LoginResponse
import javax.inject.Inject

open class RealmLoginMapper @Inject constructor() {
    fun loginResponseToUserLoginRealmModel(data: LoginResponse): LoginRealmModel {
        return LoginRealmModel(
            userName = data.userName,
            password = data.password,
            loginStatus = true
        )
    }

    fun userLoginRealmModelToLoginBusinessModel(data: LoginRealmModel): LoginBusinessModel {
        return LoginBusinessModel(
            userName = data.userName,
            password = data.password,
            loginStatus = data.loginStatus
        )
    }

}