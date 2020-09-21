package com.boltic28.networkretroroom.service.woman

import com.boltic28.networkretroroom.data.Woman
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.room.woman.WomanDao
import com.boltic28.networkretroroom.room.woman.WomanEntity
import com.boltic28.networkretroroom.service.woman.WomanService
import io.reactivex.Single
import java.util.stream.Collectors
import javax.inject.Inject

class WomanServiceImpl @Inject constructor() :
    WomanService {

    @Inject
    lateinit var dao: WomanDao

    init {
        AppDagger.component.injectService(this)
    }

    override fun create(woman: Woman): Single<Long> =
        Single.just(dao.insert(entityFrom(woman)))

    override fun update(woman: Woman): Single<Int> =
        Single.just(dao.update(entityFrom(woman)))

    override fun delete(woman: Woman): Single<Int> =
        Single.just(dao.delete(entityFrom(woman)))

    override fun getById(id: Long): Single<Woman> =
        Single.just(womanFrom(dao.getById(id)))

    override fun getAll(): Single<List<Woman>> =
        Single.just(dao.getAll().stream()
            .map { entity ->
                womanFrom(entity)
            }
            .collect(Collectors.toList())
        )

    override fun entityFrom(woman: Woman): WomanEntity =
        WomanEntity(
            woman.id, woman.title, woman.name
            , woman.lastName, woman.age, woman.gender
            , woman.date, woman.photo, woman.phone, woman.mail
        )

    override fun womanFrom(entity: WomanEntity): Woman =
        Woman(
            entity.id, entity.gender, entity.title
            , entity.name, entity.lastName, entity.age
            , entity.date, entity.photo, entity.phone, entity.mail
        )
}