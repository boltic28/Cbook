package com.boltic28.networkretroroom.service

import com.boltic28.networkretroroom.`class`.Human
import com.boltic28.networkretroroom.`class`.Man
import com.boltic28.networkretroroom.`class`.Woman
import io.reactivex.Single

interface HumanService {

    fun create(human: Human): Single<Long>
    fun readById(id: Long): Single<Man>
    fun update(human: Human): Single<Int>
    fun delete(human: Human): Single<Int>

    fun getAll(): Single<List<Human>>
    fun getAllMan(): Single<List<Man>>
    fun getAllWoman(): Single<List<Woman>>
}