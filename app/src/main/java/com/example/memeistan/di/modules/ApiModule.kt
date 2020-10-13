package com.example.dagger_android.di.modules

import com.example.dagger_android.model.RetrofitInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApiModule {


    @Provides
    fun okHTTPClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun prepareRetrofitForSignUp(): RetrofitInterface {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://memeistan.000webhostapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHTTPClient())  // We can add http interceptors later
            .build()

        return retrofitBuilder.create(RetrofitInterface::class.java)
    }


}