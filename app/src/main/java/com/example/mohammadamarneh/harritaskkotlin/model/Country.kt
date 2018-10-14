package com.example.mohammadamarneh.harritaskkotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mohammadamarneh.harritaskkotlin.db.DBTypeConverters

@Entity
@TypeConverters(DBTypeConverters::class)
class Country {
    var alpha2Code: String? = null
    var name: String? = null
    var region: String? = null
    var capital: String? = null
    @PrimaryKey
    var population: Long = 0
    var latlng: List<Float>? = null
}

