package com.boltic28.networkretroroom.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JSONRandomUserApi {
//    @GET("https://randomuser.me/api/?result=5")
//    fun getPostForUsers(): Call<Result>

    @GET("?")
    fun getPostForUsers(@Query("results") results: Int): Call<Unit>
}