package com.boltic28.networkretroroom.data.room

import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.Man
import com.boltic28.networkretroroom.data.room.man.ManEntity
import io.reactivex.Single

interface HumanService {
    fun create(human: Human): Single<Long>
    fun update(human: Human): Single<Int>
    fun delete(human: Human): Single<Int>
    fun deleteAll(): Single<Int>
    fun getById(id: Long): Single<Human>
    fun getAll(): Single<List<Human>>
    fun entityFrom(man: Man): ManEntity
    fun entityFrom(human: Human): ManEntity
    fun humanFrom(entity: ManEntity): Man
    fun humanFrom(man: Man): Human
    fun manFrom(human: Human): Man
}