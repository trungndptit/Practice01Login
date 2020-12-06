package com.example.practice01login.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practice01login.api.AppService
import com.example.practice01login.api.DiaryResponse
import com.example.practice01login.db.DiaryDao
import com.example.practice01login.db.DiaryEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class DiaryRepo(private val appService: AppService, private var diaryDao: DiaryDao) {
    fun getListDiary(): Observable<List<DiaryResponse>> = appService.getListDiaryRx()

    fun insertOrUpdateDiary(diaryResponse: DiaryResponse): Completable {
        val diaryEntity = DiaryEntity(
            diaryResponse.id,
            diaryResponse.title,
            diaryResponse.content,
            diaryResponse.date
        )
        return diaryDao.insertDiary(diaryEntity)
    }

    fun getAllDiaries() : Flowable<List<DiaryEntity>> = diaryDao.getAllDiaries()
}