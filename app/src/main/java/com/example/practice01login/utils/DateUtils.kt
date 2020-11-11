package com.example.practice01login.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun jsonDateToShortDate(jsonDate: String?): String {
        if (jsonDate == null) {
            return ""
        }
        val inFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()
        )
        val date = inFormat.parse(jsonDate) ?: return "-"



        val outputFormat = SimpleDateFormat(
            "dd/MM/yyyy", Locale.getDefault()
        )

        return outputFormat.format(date)
    }

    fun xmlDateToDate(dateString: String?): Date {
        val date = dateString ?: return Date()
        val inFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.US
        )
        return inFormat.parse(date) ?: Date()
    }

    fun getDayFromDate(date: Date): String {
        var pattern = "dd/MM/yyyy"
        val inFormat = SimpleDateFormat(
            pattern, Locale.getDefault()
        )
        return inFormat.format(date)
    }
}