package com.example.mohammadamarneh.harritaskkotlin.repo

import androidx.lifecycle.LiveData
import com.example.mohammadamarneh.harritaskkotlin.model.Weather
import com.example.mohammadamarneh.harritaskkotlin.network.ApiResponse
import com.example.mohammadamarneh.harritaskkotlin.network.ApiSuccessResponse
import com.example.mohammadamarneh.harritaskkotlin.network.NetworkConstants
import com.example.mohammadamarneh.harritaskkotlin.network.weatherApi.WeatherApiService
import com.example.mohammadamarneh.harritaskkotlin.network.weatherApi.response.GetWeatherResponse
import com.example.mohammadamarneh.harritaskkotlin.network.weatherApi.response.Main
import com.example.mohammadamarneh.harritaskkotlin.repo.utils.NetworkResourceAdapter
import com.example.mohammadamarneh.harritaskkotlin.ui.utils.Resource
import com.example.mohammadamarneh.harritaskkotlin.utils.DateUtils
import io.reactivex.Single

import java.util.ArrayList

import javax.inject.Inject

/**
 * Repository that handles Weather objects.
 */
class WeatherRepo @Inject constructor(private val apiService: WeatherApiService) {

    //returns list of two weathers, for today and tomorrow.

    fun getWeatherForecast(lat: Float, lon: Float) : LiveData<Resource<List<Weather>>>{
        return object : NetworkResourceAdapter<List<Weather>, GetWeatherResponse>(){
            override fun createCall(): Single<ApiResponse<GetWeatherResponse>> {
                return apiService.getWeatherForecast(lat, lon, NetworkConstants.OPEN_WEATHER_KEY)
            }

            override fun processSuccessResponses(response: ApiSuccessResponse<GetWeatherResponse>): List<Weather> {
                val result = ArrayList<Weather>()
                var todaySetted = false
                for (i in 0 until response.body.list.size) {
                    val dat = response.body.list[i].dt_txt
                    if (DateUtils.isToday(dat) && !todaySetted) {
                        val weather = getWeatherFromResponse(response.body.list[i].main)
                        weather.date = DateUtils.getTimeInMilliSecondsFromStringDate(dat)
                        result.add(weather)
                        todaySetted = true
                    } else if (DateUtils.isTomorrow(dat)) {
                        val weather = getWeatherFromResponse(response.body.list[i].main)
                        weather.date = DateUtils.getTimeInMilliSecondsFromStringDate(dat)
                        result.add(weather)
                    }
                }
                return result
            }

        }.asLiveData()
    }

    private fun getWeatherFromResponse(main: Main): Weather {
        val weather = Weather()
        weather.maxTemp = main.temp_max
        weather.minTemp = main.temp_min
        weather.humidity = main.humidity
        weather.pressure = main.pressure
        return weather
    }
}
