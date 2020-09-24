package com.boltic28.networkretroroom.data.network

import com.boltic28.networkretroroom.data.dto.apiresponse.Result
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUsersApi {

    @GET("?")
    fun getPostForUsers(@Query("results") results: Int): Single<Result>
}