package com.boltic28.networkretroroom.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostHuman(
    var gender: String,
    var title: String,
    var first: String,
    var last: String,
    var age: Int,
    var date: String,
    var medium: String,
    var phone: String,
    var email: String
)
//{
//
//    @SerializedName("")
//    @Expose
//    var gender: String = ""
//
//    @SerializedName("")
//    @Expose
//    var title: String = "ms"
//
//    @SerializedName("")
//    @Expose
//    var first: String = "nobody"
//
//    @SerializedName("")
//    @Expose
//    var last: String = "who"
//
//    @SerializedName("")
//    @Expose
//    var age: Int = 0
//
//    @SerializedName("")
//    @Expose
//    var date: String = ""
//
//    @SerializedName("")
//    @Expose
//    var medium: String = ""
//
//    @SerializedName("")
//    @Expose
//    var phone: String = "123456"
//
//    @SerializedName("")
//    @Expose
//    var email: String = "mail@mail.com"
//}