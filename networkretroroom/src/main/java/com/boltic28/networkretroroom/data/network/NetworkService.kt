package com.boltic28.networkretroroom.data.network

import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.apiresponse.PostHuman
import com.boltic28.networkretroroom.data.room.TypesConverter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.time.*

class NetworkService {

    private var retrofit: RetrofitClient = RetrofitClient()

    fun getUsersFromNetWork(count: Int): Single<List<Human>> =
        (retrofit.usersApi.getPostForUsers(count)
            .subscribeOn(Schedulers.io())
            .map { result ->
                result.humans.map { postHuman ->
                    extractHumanFrom(postHuman)
                }
            })

    private fun extractHumanFrom(postHuman: PostHuman): Human =
        Human(
            0, TypesConverter().genderFromString(postHuman.gender)
            , postHuman.name.title, postHuman.name.first, postHuman.name.last
            , postHuman.dob.age, parseDateToLocalDate(postHuman.dob.date)
            , postHuman.picture.medium, postHuman.phone, postHuman.email
        )

    //obtain date from 1993-07-20T09:44:18.674Z
    private fun parseDateToLocalDate(string: String): LocalDate =
        LocalDateTime.ofInstant(Instant.parse(string), ZoneId.of(ZoneOffset.UTC.id)).toLocalDate()
}