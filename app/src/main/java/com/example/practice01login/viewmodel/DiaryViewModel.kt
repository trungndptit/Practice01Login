package com.example.practice01login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practice01login.SingleLiveEvent
import com.example.practice01login.adapter.Common
import com.example.practice01login.api.DiaryResponse
import com.example.practice01login.repository.AppRepo
import com.example.practice01login.utils.DateUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    var appRepo: AppRepo? = null
    var compositeDisposable = CompositeDisposable()

    private var diary: MutableLiveData<List<DiaryResponse>>? = MutableLiveData()

    private val noInternetConnectionEvent = SingleLiveEvent<Unit>()
    private val connectTimeoutEvent = SingleLiveEvent<Unit>()

    fun getListDiary() {
        val repo = appRepo ?: return

        val disposable = repo.getListDiary()
//            .doOnError { error: Throwable -> println("Debug The error message is: " + error.message) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val listDate: MutableList<Date> = mutableListOf()
                for (diary in response) {
                    val date = DateUtils.xmlDateToDate(diary.date)
                    listDate.add(date)
                }

                listDate.sort();
                val listDiary: MutableList<DiaryResponse> = mutableListOf()
                for (date in listDate) {
                    for (diary in response) {
                        if (date.compareTo(DateUtils.xmlDateToDate(diary.date)) == 0) {
                            listDiary.add(diary)
                        }
                    }
                }
                listDiary.reverse()

                var date: String? = listDiary[0].date
                listDiary.forEachIndexed { index, diary ->
                    if (index == 0) {
                        diary.viewType = Common.VIEWTYPE_HEADER
                    } else {
                        if (DateUtils.jsonDateToShortDate(diary.date) == DateUtils.jsonDateToShortDate(
                                date
                            )
                        ) {
                            diary.viewType = Common.VIEWTYPE_DIARY
                        } else {
                            diary.viewType = Common.VIEWTYPE_HEADER
                            date = diary.date
                        }
                    }

                }

                listDiary.forEach { println("Debug log id= ${it.id} title= ${it.title} date= ${it.date}") }
                diary!!.value = listDiary
//                listDates.sortByDescending(compareBy<Date> { it.year }.thenBy { it.month }.thenBy { it.day })
            }, {
                onLoadFail(it)
            })

        compositeDisposable.add(disposable)
    }

    fun getDiary(): LiveData<List<DiaryResponse>>? {
        return this.diary
    }

    open fun onLoadFail(throwable: Throwable) {
        when (throwable.cause) {
            is UnknownHostException -> {
                noInternetConnectionEvent.value = null
            }
            is SocketTimeoutException -> {
                connectTimeoutEvent.value = null
            }
        }
    }
}