package com.boltic28.networkretroroom.service.woman

import com.boltic28.networkretroroom.data.Woman
import com.boltic28.networkretroroom.room.woman.WomanEntity
import io.reactivex.Single

interface WomanService {
    fun create(woman: Woman): Single<Long>
    fun update(woman: Woman): Single<Int>
    fun delete(woman: Woman): Single<Int>
    fun getById(id: Long): Single<Woman>
    fun getAll(): Single<List<Woman>>
    fun entityFrom(woman: Woman): WomanEntity
    fun womanFrom(entity: WomanEntity): Woman
}