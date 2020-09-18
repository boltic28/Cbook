package com.boltic28.networkretroroom.network

import com.boltic28.networkretroroom.`class`.Human
import com.boltic28.networkretroroom.`class`.Man
import com.boltic28.networkretroroom.`class`.Woman
import com.boltic28.networkretroroom.room.enum.HumanType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

class NetworkService {

//    companion object{
//        const val BASE_API_URL = "https://randomuser.me/api" // + "/?results=25   count of people which you need"
//    }
//
//    private var retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    fun getUsers(count: Int): List<Human> {
//        val people = mutableListOf<Human>()
//        this
//            .getRandomUserApi()
//            .getPostForUsers(count)
//            .enqueue(object : Callback<List<PostHuman>> {
//                override fun onFailure(call: Call<List<PostHuman>>, t: Throwable) {
//                    // smth wrong
//                }
//
//                override fun onResponse(
//                    call: Call<List<PostHuman>>,
//                    response: Response<List<PostHuman>>
//                ) {
//                    val list = response.body()
//                    if (list != null) {
//                        people.addAll(list.stream()
//                            .map { postHuman -> extractHumanFrom(postHuman) }
//                            .collect(Collectors.toList())
//                        )
//                    }
//                }
//            })
//        return people
//    }
//
//    private fun getRandomUserApi(): JSONRandomUserApi = retrofit.create(JSONRandomUserApi::class.java)
//
//
//    fun extractHumanFrom(postHuman: PostHuman) =
//        if (postHuman.gender == "male"){
//            Man(0, HumanType.MAN, postHuman.title, postHuman.first
//                , postHuman.last, postHuman.age, parseDateToLocalDate(postHuman.date)
//                , postHuman.medium, postHuman.phone, postHuman.email)
//        }else{
//            Woman(0, HumanType.WOMAN, postHuman.title, postHuman.first
//                , postHuman.last, postHuman.age, parseDateToLocalDate(postHuman.date)
//                , postHuman.medium, postHuman.phone, postHuman.email)
//        }
//
//    //input 1993-07-20T09:44:18.674Z
//    private fun parseDateToLocalDate(string: String): LocalDate {
//        string.substring(0, 10)
//        return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH))
//    }
}

interface JSONRandomUserApi {
    @GET("/?results={count}")
    fun getPostForUsers(@Path("count") count: Int): Call<List<PostHuman>>
}