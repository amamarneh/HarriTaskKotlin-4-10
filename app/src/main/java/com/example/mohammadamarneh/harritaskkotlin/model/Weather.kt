package com.example.mohammadamarneh.harritaskkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Weather {
    @PrimaryKey
    var date: Long = 0 //in milliseconds
    var maxTemp: Float = 0.toFloat()
    var minTemp: Float = 0.toFloat()
    var pressure: Float = 0.toFloat()
    var humidity: Int = 0
}