package com.example.practice01login.api

data class DiaryResponse(
    var id: String,
    var title : String,
    var content : String,
    var date : String,
    var viewType : Int
) {
}