package com.example.dagger_android.model

import com.example.memeistan.data.model.json.LoginResponse
import com.example.memeistan.data.model.json.SignUpResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface RetrofitInterface {

    @FormUrlEncoded
    @POST("signUp.php")
    fun getSignUpResponse(
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("imageString") imageString: String
    ): Observable<SignUpResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun getLoginResponse(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): Observable<LoginResponse>


}