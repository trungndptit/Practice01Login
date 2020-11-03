package com.example.practice01login.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    var userId: Long? = null,
    var username: String = "",
    var xAcc: String = ""
)