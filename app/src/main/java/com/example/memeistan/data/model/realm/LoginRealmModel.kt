package com.example.memeistan.data.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LoginRealmModel(
    @PrimaryKey
    var userName: String = "",
    var password: String = "",
    var loginStatus: Boolean = false
) : RealmObject()