package com.example.practice01login.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryEntity::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        private var instance: DiaryDatabase? = null
        fun getInstance(context: Context): DiaryDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java, "Diary"
                ).build()
            }

            return instance as DiaryDatabase
        }
    }
}