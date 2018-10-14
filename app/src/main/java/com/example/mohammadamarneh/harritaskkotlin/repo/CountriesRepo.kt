package com.example.mohammadamarneh.harritaskkotlin.repo

import androidx.lifecycle.LiveData
import com.example.mohammadamarneh.harritaskkotlin.di.CountriesApi
import com.example.mohammadamarneh.harritaskkotlin.model.Country
import com.example.mohammadamarneh.harritaskkotlin.network.ApiResponse
import com.example.mohammadamarneh.harritaskkotlin.network.ApiSuccessResponse
import com.example.mohammadamarneh.harritaskkotlin.network.CountriesApiService
import com.example.mohammadamarneh.harritaskkotlin.repo.utils.NetworkResourceAdapter
import com.example.mohammadamarneh.harritaskkotlin.ui.utils.Resource

import javax.inject.Inject

import io.reactivex.Single
import javax.inject.Singleton

/**
 * Repository that handles Country objects.
 */
@Singleton
class CountriesRepo @Inject constructor(@CountriesApi private val countriesApiService: CountriesApiService) {

    val allCountries: LiveData<Resource<List<Country>>>
        get() = object : NetworkResourceAdapter<List<Country>, List<Country>>() {
                    override fun createCall() = countriesApiService.allCountries
                }.asLiveData()

}
