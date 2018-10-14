package com.example.mohammadamarneh.harritaskkotlin.network

object NetworkConstants {
    val COUNTRIES_END_POINT = "https://restcountries.eu/"
    val WETHER_END_POINT = "http://api.openweathermap.org/"
    val OPEN_WEATHER_KEY = "1867722b6af87e1d0388e10c5a94be34"

    fun getFlagUrl(countryCode: String) = "http://www.geognos.com/api/en/countries/flag/$countryCode.png"
}
