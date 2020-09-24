package com.boltic28.networkretroroom.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{

    private val baseURL = "https://randomuser.me/api/"
    private var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder()
    private val retrofit: Retrofit

    val usersApi: GetUsersApi

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        usersApi = retrofit.create(GetUsersApi::class.java)
    }
}