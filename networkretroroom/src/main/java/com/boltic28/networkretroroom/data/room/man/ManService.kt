package com.boltic28.networkretroroom.data.room.man

import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.Man
import com.boltic28.networkretroroom.data.dto.Woman
import com.boltic28.networkretroroom.data.room.man.ManEntity
import io.reactivex.Single

interface ManService {
    fun create(man: Man): Single<Long>
    fun update(man: Man): Single<Int>
    fun delete(man: Man): Single<Int>
    fun deleteAll(): Single<Int>
    fun getById(id: Long): Single<Man>
    fun getAll(): Single<List<Man>>
    fun entityFrom(man: Man): ManEntity
    fun entityFrom(human: Human): ManEntity
    fun humanFrom(entity: ManEntity): Man
    fun humanFrom(man: Man): Human
    fun manFrom(human: Human): Man
}