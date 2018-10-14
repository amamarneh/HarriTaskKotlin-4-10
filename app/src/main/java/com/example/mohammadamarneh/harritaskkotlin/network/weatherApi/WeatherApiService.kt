package com.example.mohammadamarneh.harritaskkotlin.network.weatherApi


import com.example.mohammadamarneh.harritaskkotlin.network.ApiResponse
import com.example.mohammadamarneh.harritaskkotlin.network.weatherApi.response.GetWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/forecast")
    fun getWeatherForecast(@Query("lat") lat: Float, @Query("lon") lon: Float, @Query("appid") appId: String): Single<ApiResponse<GetWeatherResponse>>

}
