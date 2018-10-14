package com.example.mohammadamarneh.harritaskkotlin.ui.countryActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

import com.example.mohammadamarneh.harritaskkotlin.model.Country
import com.example.mohammadamarneh.harritaskkotlin.model.Weather
import com.example.mohammadamarneh.harritaskkotlin.repo.CountriesRepo
import com.example.mohammadamarneh.harritaskkotlin.repo.WeatherRepo
import com.example.mohammadamarneh.harritaskkotlin.ui.utils.Resource

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CountryActivityViewModel @Inject constructor(private val countriesRepo: CountriesRepo, private val weatherRepo: WeatherRepo) : ViewModel() {
    var resultLive: MutableLiveData<Resource<List<Country>>>? = null
    var weatherLive: MutableLiveData<Resource<List<Weather>>>? = null

    var selectedCountry: Country? = null

    var lastLat: Float? = null
    var lastLon: Float? = null

    fun init() {
        if (resultLive != null && weatherLive != null) {
            return
        }

        resultLive = MutableLiveData()
        weatherLive = MutableLiveData()
        retry()
    }

    fun getWeatherForTodayAndTomorrow(lat: Float, lon: Float) {
        lastLat = lat
        lastLon = lon
        retryWeather()
    }

    fun retry() {
        countriesRepo.allCountries.observeForever { r -> resultLive?.postValue(r) }
    }

    fun retryWeather() {
        weatherRepo.getWeatherForecast(lastLat!!, lastLon!!).observeForever{ r -> weatherLive?.postValue(r) }
    }

}
