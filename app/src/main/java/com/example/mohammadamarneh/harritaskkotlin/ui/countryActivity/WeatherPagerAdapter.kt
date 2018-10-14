package com.example.mohammadamarneh.harritaskkotlin.ui.countryActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mohammadamarneh.harritaskkotlin.model.Weather
import com.example.mohammadamarneh.harritaskkotlin.ui.countryWeatherFragment.CountryWeatherFragment

class WeatherPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    private val weatherFragments: Array<CountryWeatherFragment?>

    init {
        weatherFragments = arrayOfNulls(count)
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        if (weatherFragments[position] == null) {
            weatherFragments[position] = CountryWeatherFragment.newInstance()
        }
        return weatherFragments[position]!!
    }

    override fun getCount() = 2

    fun updateData(weathers: List<Weather>) {
        if (weatherFragments[0] != null && weathers.isNotEmpty())
            weatherFragments[0]!!.showWeather(weathers[0])

        if (weatherFragments[1] != null && weathers.size > 1)
            weatherFragments[1]!!.showWeather(weathers[1])
    }
}