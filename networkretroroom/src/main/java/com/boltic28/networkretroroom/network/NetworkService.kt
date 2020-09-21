package com.boltic28.networkretroroom.network

import com.boltic28.networkretroroom.data.Human
import com.boltic28.networkretroroom.room.TypesConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import trikita.log.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

class NetworkService {

    companion object{
        const val BASE_API_URL = "https://randomuser.me/api/"
    }

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsersFromNetWork(count: Int): List<Human> {
        val people = mutableListOf<Human>()

        getRandomUserApi()
            .getPostForUsers(count)
            .enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("network error: $t")
                }

                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    val list = response.body().toString()

                    Log.d("network response: $list")
//                    if (list != null) {
//                        people.addAll(list.stream()
//                            .map { postHuman -> extractHumanFrom(postHuman) }
//                            .collect(Collectors.toList())
//                        )
//                    }
                }
            })
        return people
    }

    private fun getRandomUserApi(): JSONRandomUserApi = retrofit.create(JSONRandomUserApi::class.java)


    private fun extractHumanFrom(postHuman: PostHuman): Human {
        Log.d("network new Human: ${postHuman.first}")
        return Human(
            0, TypesConverter().genderFromString(postHuman.gender)
            , postHuman.title, postHuman.first, postHuman.last
            , postHuman.age, parseDateToLocalDate(postHuman.date)
            , postHuman.medium, postHuman.phone, postHuman.email
        )
    }
    //input 1993-07-20T09:44:18.674Z
    private fun parseDateToLocalDate(string: String): LocalDate {
        string.substring(0, 10)
        return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH))
    }
}