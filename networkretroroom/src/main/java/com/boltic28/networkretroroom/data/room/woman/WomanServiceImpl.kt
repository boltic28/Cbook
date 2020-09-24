package com.boltic28.networkretroroom.data.room.woman

import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.Woman
import com.boltic28.networkretroroom.data.room.TypesConverter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors
import javax.inject.Inject

class WomanServiceImpl @Inject constructor() :
    WomanService {

    @Inject
    lateinit var dao: WomanDao

    private var converter = TypesConverter()

    init {
        AppDagger.component.injectService(this)
    }

    override fun create(woman: Woman): Single<Long> =
        dao.insert(entityFrom(woman)).subscribeOn(Schedulers.io())

    override fun update(woman: Woman): Single<Int> =
        dao.update(entityFrom(woman)).subscribeOn(Schedulers.io())

    override fun delete(woman: Woman): Single<Int> =
        dao.delete(entityFrom(woman)).subscribeOn(Schedulers.io())

    override fun deleteAll(): Single<Int> =
        dao.deleteAll().subscribeOn(Schedulers.io())

    override fun getById(id: Long): Single<Woman> =
        dao.getById(id)
            .map { womanFrom(it) }.subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Human>> =
        dao.getAll().map { entityList ->
            entityList.stream()
                .map { entity ->
                    humanFrom(entity)
                }
                .collect(Collectors.toList())
        }.subscribeOn(Schedulers.io())

    override fun entityFrom(woman: Woman): WomanEntity =
        WomanEntity(
            woman.id, woman.title, woman.name
            , woman.lastName, woman.age, converter.genderToString(woman.gender)
            , converter.dateToTimestamp(woman.date), woman.photo, woman.phone, woman.mail
        )

    override fun entityFrom(human: Human): WomanEntity =
        WomanEntity(
            human.id, human.title, human.name
            , human.lastName, human.age, converter.genderToString(human.gender)
            , converter.dateToTimestamp(human.date), human.photo, human.phone, human.mail
        )

    override fun womanFrom(entity: WomanEntity): Woman =
        Woman(
            entity.id, converter.genderFromString(entity.gender), entity.title
            , entity.name, entity.lastName, entity.age
            , converter.dateFromTimestamp(entity.date), entity.photo, entity.phone, entity.mail
        )

    override fun humanFrom(entity: WomanEntity): Human =
        Human(
            entity.id, converter.genderFromString(entity.gender), entity.title
            , entity.name, entity.lastName, entity.age
            , converter.dateFromTimestamp(entity.date), entity.photo, entity.phone, entity.mail
        )

    override fun humanFrom(woman: Woman): Human =
        Human(
            woman.id, woman.gender, woman.title
            , woman.name, woman.lastName, woman.age
            , woman.date, woman.photo, woman.phone, woman.mail
        )

    override fun womanFrom(human: Human): Woman =
        Woman(
            human.id, human.gender, human.title
            , human.name, human.lastName, human.age
            , human.date, human.photo, human.phone, human.mail
        )
}