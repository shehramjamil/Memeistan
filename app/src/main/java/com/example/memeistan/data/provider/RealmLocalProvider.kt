package com.example.memeistan.data.provider

import com.example.memeistan.business.model.LoginBusinessModel
import com.example.memeistan.business.provider.LocalProvider
import com.example.memeistan.data.model.realm.LoginRealmModel
import com.example.memeistan.data.model.realm.mapper.RealmLoginMapper
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

open class RealmLocalProvider @Inject constructor(private val realmLoginMapper: RealmLoginMapper) :
    LocalProvider {

    override fun insertLoginData(loginData: LoginRealmModel): Observable<Boolean> {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.copyToRealm(loginData)
        }
        realm.close()
        return Observable.just(true)
    }

    override fun readLoggedUserData(): Observable<LoginBusinessModel> {
        val realm = Realm.getDefaultInstance()
        val realmLoginData = LoginRealmModel().queryFirst()
        when {
            realm.isEmpty -> {
                realm.close()
                return Observable.just(
                    realmLoginMapper.userLoginRealmModelToLoginBusinessModel(realmLoginData!!)
                )
            }
            else -> {
                return Observable.just(
                    realmLoginMapper.userLoginRealmModelToLoginBusinessModel(
                        realmLoginData!!
                    )
                )
            }
        }
    }

    override fun deleteLoginData(): Observable<Boolean> {
        LoginRealmModel().deleteAll()
        return Observable.just(true)
    }
}