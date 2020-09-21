package com.boltic28.networkretroroom.service.man

import com.boltic28.networkretroroom.data.Man
import com.boltic28.networkretroroom.room.man.ManEntity
import io.reactivex.Single

interface ManService {
    fun create(man: Man): Single<Long>
    fun update(man: Man): Single<Int>
    fun delete(man: Man): Single<Int>
    fun getById(id: Long): Single<Man>
    fun getAll(): Single<List<Man>>
    fun entityFrom(man: Man): ManEntity
    fun manFrom(entity: ManEntity): Man
}