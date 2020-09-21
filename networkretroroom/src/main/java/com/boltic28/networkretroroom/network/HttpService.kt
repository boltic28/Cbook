package com.boltic28.networkretroroom.network

import com.boltic28.networkretroroom.data.Human
import okhttp3.*
import trikita.log.Log
import java.io.IOException

class HttpService {


//    private val BASE_URL = "https://randomuser.me/api/?result=%d"
    private val BASE_URL = "https://randomuser.me/api/?gender=female"

    private val client = OkHttpClient()


    fun getUsersFromNet(count: Int): List<Human> {
//        val url = String.format(BASE_URL, count)
        val url = String.format(BASE_URL)
        val request = Request.Builder().url(url).build()
        val people = mutableListOf<Human>()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("OKHTTP: FAILURE $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: ResponseBody? = response.body()
                val line = responseBody.toString()
                Log.d("OKHTTP: SUCCESS ${line}")
            }
        })

        return people
    }
}