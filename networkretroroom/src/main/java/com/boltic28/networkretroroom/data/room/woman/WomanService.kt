package com.boltic28.networkretroroom.data.room.woman

import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.Woman
import com.boltic28.networkretroroom.data.room.woman.WomanEntity
import io.reactivex.Single

interface WomanService {
    fun create(woman: Woman): Single<Long>
    fun update(woman: Woman): Single<Int>
    fun delete(woman: Woman): Single<Int>
    fun deleteAll(): Single<Int>
    fun getById(id: Long): Single<Woman>
    fun getAll(): Single<List<Woman>>
    fun entityFrom(woman: Woman): WomanEntity
    fun entityFrom(human: Human): WomanEntity
    fun womanFrom(entity: WomanEntity): Woman
    fun humanFrom(woman: Woman): Human
    fun womanFrom(human: Human): Woman
}