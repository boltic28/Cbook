package com.boltic28.networkretroroom.service.man

import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.Man
import com.boltic28.networkretroroom.room.man.ManDao
import com.boltic28.networkretroroom.room.man.ManEntity
import com.boltic28.networkretroroom.service.man.ManService
import io.reactivex.Single
import java.util.stream.Collectors
import javax.inject.Inject

class ManServiceImpl @Inject constructor():
    ManService {

    @Inject
    lateinit var dao: ManDao

    init {
        AppDagger.component.injectService(this)
    }

    override fun create(man: Man): Single<Long> =
        Single.just(dao.insert(entityFrom(man)))

    override fun update(man: Man): Single<Int> =
        Single.just(dao.update(entityFrom(man)))

    override fun delete(man: Man): Single<Int> =
        Single.just(dao.delete(entityFrom(man)))

    override fun getById(id: Long): Single<Man> =
        Single.just(manFrom(dao.getById(id)))

    override fun getAll(): Single<List<Man>> =
        Single.just(dao.getAll().stream()
            .map { entity ->
                manFrom(entity)
            }
            .collect(Collectors.toList())
        )

    override fun entityFrom(man: Man): ManEntity =
        ManEntity(
            man.id, man.title, man.name
            , man.lastName, man.age, man.gender
            , man.date, man.photo, man.phone, man.mail
        )

    override fun manFrom(entity: ManEntity): Man =
        Man(
            entity.id, entity.gender, entity.title
            , entity.name, entity.lastName, entity.age
            , entity.date, entity.photo, entity.phone, entity.mail
        )
}