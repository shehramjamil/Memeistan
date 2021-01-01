package com.example.memeistan.business.usecases

import com.example.dagger_android.model.RetrofitInterface
import com.example.memeistan.data.model.json.SignUpResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    fun execute(
        userName: String,
        password: String,
        gender: String,
        imageString: String
    ): Observable<SignUpResponse> {
        return retrofitInterface.getSignUpResponse(userName, password, gender, imageString)
    }
}