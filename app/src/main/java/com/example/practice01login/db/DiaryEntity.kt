package com.example.practice01login.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practice01login.adapter.Common
import com.example.practice01login.api.DiaryResponse

@Entity
data class DiaryEntity(
    var id: String,
    var title: String,
    var content: String,
    var date: String,
    var viewType : Int = Common.VIEWTYPE_DIARY
) {
    @PrimaryKey(autoGenerate = true)
    var diaryId: Long = 0
}