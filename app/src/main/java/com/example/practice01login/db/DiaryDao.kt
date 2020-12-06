package com.example.practice01login.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diaryEntity : DiaryEntity) : Completable

    @Query("SELECT * FROM DiaryEntity")
    fun getAllDiaries() : Flowable<List<DiaryEntity>>

    @Query("DELETE FROM DiaryEntity WHERE diaryId = :id")
    fun deleteDiaryById(id: Long)
}