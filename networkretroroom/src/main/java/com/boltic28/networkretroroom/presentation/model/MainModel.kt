package com.boltic28.networkretroroom.presentation.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.boltic28.networkretroroom.util.DiffUtilUsers
import com.boltic28.networkretroroom.presentation.HumanAdapter
import com.boltic28.networkretroroom.util.NetworkChecker
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.network.NetworkService
import com.boltic28.networkretroroom.data.room.enum.Gender
import com.boltic28.networkretroroom.data.room.man.ManServiceImpl
import com.boltic28.networkretroroom.data.room.woman.WomanServiceImpl
import com.boltic28.networkretroroom.util.Messenger
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.BiPredicate
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainModel : ViewModel() {

    @Inject
    lateinit var network: NetworkService

    @Inject
    lateinit var manService: ManServiceImpl

    @Inject
    lateinit var womanService: WomanServiceImpl

    @Inject
    lateinit var networkChecker: NetworkChecker

    @Inject
    lateinit var messenger: Messenger

    private var isNetConnected: Boolean
    private var tag = "csfgt"
    private val countOfTriesToConnectNetwork = 1
    private val countOfPeopleFromNetwork = 10

    lateinit var disposable: Disposable
    lateinit var adapter: HumanAdapter

    init {
        AppDagger.component.injectModel(this)
        isNetConnected = networkChecker.checkInternetConnection()
    }

    @SuppressLint("CheckResult")
    fun loadData() {
        disposable = if (isNetConnected) {
            getDataFromNet()
        } else {
            Log.d(tag, "Internet not available")
            messenger.showMessage("Internet not available")
            getDataFromDB()
        }
    }

    @SuppressLint("CheckResult")
    fun getDataFromDB(): Disposable {
        Log.d(tag, "Try to get data from DB")
        return Single.zip(
            manService.getAll(),
            womanService.getAll(),
            BiFunction<List<Human>, List<Human>, List<Human>> { men, women -> men + women })
            .doOnError{
                messenger.showMessage("Cannot read data from database")
                Log.d(tag, "ERROR DB $it")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d(tag, "Loading data into adapter")
                adapter.refreshData(list)
            }, {
                messenger.showMessage("Cannot read data from database")
                Log.d(tag, "ERROR DB $it")
            })
    }

    @SuppressLint("CheckResult")
    fun getDataFromNet(): Disposable {
        return  network.getUsersFromNetWork(countOfPeopleFromNetwork)
            .retry(BiPredicate<Int, Throwable> { number, err ->
                if (number == countOfTriesToConnectNetwork){
                    Log.d(tag, "$number-st try to get data from NET...")
                    Log.d(tag, "Cannot to get data from NET")
                    messenger.showMessage("Cannot get data from network")
                    return@BiPredicate false
                }else{
                    Log.d(tag, "$number-st try to get data from NET")
                    Log.d(tag, "ERROR NET $err")
                    return@BiPredicate true
                }
            })
            .flatMap { list ->
                writeDataIntoDB(list)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                messenger.showMessage("Database is refreshed")
                Log.d(tag, "Loading data into adapter")
                adapter.refreshData(list)
            },{
                Log.d(tag, "Network or Writing Error: $it")
                getDataFromDB()
            })
    }

    @SuppressLint("CheckResult")
    private fun writeDataIntoDB(humans: List<Human>): Single<List<Human>> {
        Log.d(tag, "Data is gotten from NET: ${humans.size} items")
        Log.d(tag, "Try to write data into DB")
        return Single.zip(
            manService.deleteAll(),
            womanService.deleteAll(),
            BiFunction<Int, Int, Int> { men, women -> men + women })
            .doOnSuccess { deleted ->
                Log.d(tag, "Cleaning of the DB...")
                Log.d(tag, "users deleted: $deleted")
            }
            .flatMap {
                var result = Single.just(mutableListOf<Human>())
                Log.d(tag, "start of writing data into DB")
                humans.forEach { human ->
                    if (human.gender == Gender.MAN) {
                        result = Single.zip(result,
                            manService.create(manService.manFrom(human))
                                .map { id ->
                                    Log.d(tag, "DB - data wrote $id - man")
                                    human.copy(id = id)
                                }
                            , BiFunction { list, item ->
                                list.add(item)
                                return@BiFunction list
                            })
                    } else {
                        result = Single.zip(result,
                            womanService.create(womanService.womanFrom(human))
                                .map { id ->
                                    Log.d(tag, "DB - data wrote $id - woman")
                                    human.copy(id = id)
                                }
                            , BiFunction { list, item ->
                                list.add(item)
                                return@BiFunction list
                            })
                    }
                }

                return@flatMap result
            }
    }

}