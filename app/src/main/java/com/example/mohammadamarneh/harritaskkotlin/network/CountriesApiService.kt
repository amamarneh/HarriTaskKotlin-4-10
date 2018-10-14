package com.example.mohammadamarneh.harritaskkotlin.network

import com.example.mohammadamarneh.harritaskkotlin.model.Country

import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApiService {

    @get:GET("rest/v1/all")
    val allCountries: Single<ApiResponse<List<Country>>>

}
