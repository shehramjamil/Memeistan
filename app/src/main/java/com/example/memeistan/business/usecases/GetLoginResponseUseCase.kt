package com.example.memeistan.business.usecases

import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.data.model.json.LoginResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetLoginResponseUseCase @Inject constructor(private val retrofitInterface: RetrofitInterface) {
    fun execute(userName: String, password: String): Observable<LoginResponse> {
        return retrofitInterface.getLoginResponse(userName, password)
    }

}