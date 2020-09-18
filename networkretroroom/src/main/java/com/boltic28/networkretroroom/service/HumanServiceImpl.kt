package com.boltic28.networkretroroom.service

import android.content.Context
import com.boltic28.networkretroroom.`class`.Human
import com.boltic28.networkretroroom.`class`.Man
import com.boltic28.networkretroroom.`class`.Woman
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.room.AppDataBase
import com.boltic28.networkretroroom.room.enum.HumanType
import com.boltic28.networkretroroom.room.human.HumanDao
import com.boltic28.networkretroroom.room.human.HumanEntity
import io.reactivex.Single
import java.util.stream.Collectors
import javax.inject.Inject

class HumanServiceImpl(private val context: Context) : HumanService {

    lateinit var humanDao: HumanDao

    init {
        val db = AppDataBase.getInstance(context)
        humanDao = db.humanDao()
//        AppDagger.component.injectService(this)
    }

    override fun create(human: Human): Single<Long> {
        return humanDao.insert(human.convertToEntity())
    }

    override fun readById(id: Long): Single<Man> {
        return humanDao.getById(id).map { toMan(it) }
    }

    override fun update(human: Human): Single<Int> {
        return humanDao.update(human.convertToEntity())
    }

    override fun delete(human: Human): Single<Int> {
        return humanDao.delete(human.convertToEntity())
    }

    override fun getAll(): Single<List<Human>> {
        return humanDao.getAll()
            .map {entityList ->
                entityList.stream()
                    .map { entity ->
                        toHuman(entity) }
                    .collect(Collectors.toList())
            }
    }

    override fun getAllMan(): Single<List<Man>> {
        return humanDao.getAll()
            .map {entityList ->
                entityList.stream()
                    .map { entity ->
                        toMan(entity) }
                    .collect(Collectors.toList())
            }
    }

    override fun getAllWoman(): Single<List<Woman>> {
        return humanDao.getAll()
            .map {entityList ->
                entityList.stream()
                    .map { entity ->
                        toWoman(entity) }
                    .collect(Collectors.toList())
            }
    }


    //    override fun create(human: Human): Single<Long> =
//        Single.just(humanDao.insert(human.convertToEntity()))
//
//    override fun readById(id: Long) : Single<Man> =
//        Single.just(toMan(humanDao.getById(id)))
//
//    override fun update(human: Human) : Single<Int> =
//        Single.just(humanDao.update(human.convertToEntity()))
//
//    override fun delete(human: Human) : Single<Int> =
//        Single.just(humanDao.delete(human.convertToEntity()))
//
//    override fun getAll(): Single<List<Man>> {
//        return Single.just(
//            humanDao.getAll().stream()
//                .map { entity -> toMan(entity) }
//                .collect(Collectors.toList()))
//    }
//
//    override fun getAllMan(): Single<List<Man>> {
//        return Single.just(
//            humanDao.getAllBySex(HumanType.MAN.value).stream()
//            .map { entity -> toMan(entity) }
//            .collect(Collectors.toList()))
//    }
//
//    override fun getAllWoman(): Single<List<Man>> {
//        return Single.just(
//            humanDao.getAllBySex(HumanType.MAN.value).stream()
//                .map { entity -> toMan(entity) }
//                .collect(Collectors.toList()))
//    }
//
    private fun toHuman(human: HumanEntity) =
        if (human.type == HumanType.MAN) {
            toMan(human)
        } else {
            toWoman(human)
        }

    private fun toMan(entity: HumanEntity) = Man(
        entity.id, entity.type, entity.title
        , entity.lastName, entity.lastName, entity.age
        , entity.date, entity.photo, entity.phone, entity.mail
    )

    private fun toWoman(entity: HumanEntity) = Woman(
        entity.id, entity.type, entity.title
        , entity.lastName, entity.lastName, entity.age
        , entity.date, entity.photo, entity.phone, entity.mail
    )
}