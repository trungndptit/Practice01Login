package com.example.practice01login.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insertUser(userEntity : UserEntity) : Long

    @Query("SELECT * FROM UserEntity WHERE userId = :userId")
    fun loadUserWithId(userId: Long): UserEntity

    @Query("UPDATE UserEntity SET xAcc = :xAcc WHERE userId = :userId")
    fun updateXAccWithId(userId: Long, xAcc : String)

    @Query("SELECT * FROM UserEntity")
    fun loadAllUsers() : List<UserEntity>
}