package com.example.mohammadamarneh.harritaskkotlin.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    fun isToday(strDate: String) = android.text.format.DateUtils.isToday(getTimeInMilliSecondsFromStringDate(strDate))

    fun isToday(timeInMilliSeconds: Long) = android.text.format.DateUtils.isToday(timeInMilliSeconds)

    @SuppressLint("SimpleDateFormat")
    fun getTimeInMilliSecondsFromStringDate(strDate: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:s")
        var convertedDate: Date? = null
        try {
            convertedDate = dateFormat.parse(strDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return convertedDate?.time ?: 0
    }

    fun isTomorrow(strDate: String) = isTomorrow(getTimeInMilliSecondsFromStringDate(strDate))

    fun isTomorrow(timeInMilliSeconds: Long): Boolean {
        val startOfTomorrow = Calendar.getInstance()
        startOfTomorrow.add(Calendar.DAY_OF_YEAR, 1)
        startOfTomorrow.set(Calendar.HOUR_OF_DAY, 0)
        startOfTomorrow.set(Calendar.MINUTE, 0)
        startOfTomorrow.set(Calendar.SECOND, 0)

        val endOfTomorrow = Calendar.getInstance()
        endOfTomorrow.add(Calendar.DAY_OF_YEAR, 1)
        endOfTomorrow.set(Calendar.HOUR_OF_DAY, 23)
        endOfTomorrow.set(Calendar.MINUTE, 59)
        endOfTomorrow.set(Calendar.SECOND, 59)

        return timeInMilliSeconds > startOfTomorrow.timeInMillis && timeInMilliSeconds < endOfTomorrow.timeInMillis
    }

    fun format(date: Long): String {
        val dateFormat = SimpleDateFormat("EEE, d/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(date))
    }
}
