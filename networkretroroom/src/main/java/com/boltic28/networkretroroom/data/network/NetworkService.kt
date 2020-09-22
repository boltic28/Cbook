package com.boltic28.networkretroroom.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.apiresponse.PostHuman
import com.boltic28.networkretroroom.data.dto.apiresponse.Result
import com.boltic28.networkretroroom.data.room.TypesConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import trikita.log.Log
import java.time.*
import java.util.stream.Collectors

class NetworkService {

    companion object{
        const val BASE_API_URL = "https://randomuser.me/api/"
    }

    private val _humans = MutableLiveData<List<Human>>()
    val humans: LiveData<List<Human>>
            get() = _humans

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsersFromNetWork(count: Int) {

        var errCounter = 0
        getRandomUserApi()
            .getPostForUsers(count)
            .enqueue(object : Callback<Result> {
                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Log.d("network error: $t")
                    errCounter++
                    if (errCounter < 5) getUsersFromNetWork(count)
                }

                override fun onResponse(
                    call: Call<Result>,
                    response: Response<Result>
                ) {
                    val humans = response.body()?.humans

                    if (humans != null) {
                        val updatedHumans = humans.map { entity ->
                            extractHumanFrom(entity)
                        }
                        _humans.postValue(updatedHumans)
                    }
                }
            })
    }

    private fun getRandomUserApi(): GetUsersFromNet = retrofit.create(GetUsersFromNet::class.java)


    private fun extractHumanFrom(postHuman: PostHuman): Human {
        return Human(
            0, TypesConverter().genderFromString(postHuman.gender)
            , postHuman.name.title, postHuman.name.first, postHuman.name.last
            , postHuman.dob.age, parseDateToLocalDate(postHuman.dob.date)
            , postHuman.picture.medium, postHuman.phone, postHuman.email
        )
    }

    //input 1993-07-20T09:44:18.674Z
    private fun parseDateToLocalDate(string: String): LocalDate {
        val date = LocalDateTime.ofInstant(Instant.parse(string), ZoneId.of(ZoneOffset.UTC.id)).toLocalDate()
        Log.d("line for parsing $date")
        return date
    }
}