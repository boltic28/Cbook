package com.boltic28.networkretroroom.data.room.man

import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.dto.Man
import com.boltic28.networkretroroom.data.room.TypesConverter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors
import javax.inject.Inject

class ManServiceImpl @Inject constructor() :
    ManService {

    @Inject
    lateinit var dao: ManDao

    private var converter = TypesConverter()

    init {
        AppDagger.component.injectService(this)
    }

    override fun create(man: Man): Single<Long> =
        dao.insert(entityFrom(man)).subscribeOn(Schedulers.io())

    override fun update(man: Man): Single<Int> =
        dao.update(entityFrom(man)).subscribeOn(Schedulers.io())

    override fun delete(man: Man): Single<Int> =
        dao.delete(entityFrom(man)).subscribeOn(Schedulers.io())

    override fun deleteAll(): Single<Int> =
        dao.deleteAll().subscribeOn(Schedulers.io())

    override fun getById(id: Long): Single<Man> =
        dao.getById(id)
            .map { manFrom(it) }.subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Human>> =
        dao.getAll().map { entityList ->
            entityList.stream()
                .map { entity ->
                    humanFrom(entity)
                }
                .collect(Collectors.toList())
        }.subscribeOn(Schedulers.io())

    override fun entityFrom(man: Man): ManEntity =
        ManEntity(
            man.id, man.title, man.name
            , man.lastName, man.age, converter.genderToString(man.gender)
            , converter.dateToTimestamp(man.date), man.photo, man.phone, man.mail
        )

    override fun entityFrom(human: Human): ManEntity =
        ManEntity(
            human.id, human.title, human.name
            , human.lastName, human.age, converter.genderToString(human.gender)
            , converter.dateToTimestamp(human.date), human.photo, human.phone, human.mail
        )

    override fun manFrom(entity: ManEntity): Man =
        Man(
            entity.id, converter.genderFromString(entity.gender), entity.title
            , entity.name, entity.lastName, entity.age
            , converter.dateFromTimestamp(entity.date), entity.photo, entity.phone, entity.mail
        )

    override fun humanFrom(entity: ManEntity): Human =
        Human(
            entity.id, converter.genderFromString(entity.gender), entity.title
            , entity.name, entity.lastName, entity.age
            , converter.dateFromTimestamp(entity.date), entity.photo, entity.phone, entity.mail
        )

    override fun humanFrom(man: Man): Human =
        Human(
            man.id, man.gender, man.title
            , man.name, man.lastName, man.age
            , man.date, man.photo, man.phone, man.mail
        )

    override fun manFrom(human: Human): Man =
        Man(
            human.id, human.gender, human.title
            , human.name, human.lastName, human.age
            , human.date, human.photo, human.phone, human.mail
        )
}