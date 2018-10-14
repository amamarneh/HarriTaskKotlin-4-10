package com.example.mohammadamarneh.harritaskkotlin.base

import com.example.mohammadamarneh.harritaskkotlin.di.CountriesApi
import com.example.mohammadamarneh.harritaskkotlin.di.WeatherApi
import com.example.mohammadamarneh.harritaskkotlin.network.CountriesApiService
import com.example.mohammadamarneh.harritaskkotlin.network.NetworkConstants
import com.example.mohammadamarneh.harritaskkotlin.network.utils.RxSingleCallAdapter
import com.example.mohammadamarneh.harritaskkotlin.network.utils.RxSingleCallAdapterFactory
import com.example.mohammadamarneh.harritaskkotlin.network.weatherApi.WeatherApiService
import com.example.mohammadamarneh.harritaskkotlin.viewModel.ViewModelModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides @Singleton @CountriesApi
    fun provideCountriesRetrofit(httpClient: OkHttpClient, @CountriesApi baseUrl: String) = createRetrofit(httpClient, baseUrl)

    @Provides @Singleton @WeatherApi
    fun provideWeatherRetrofit(httpClient: OkHttpClient, @WeatherApi baseUrl: String) = createRetrofit(httpClient, baseUrl)

    private fun createRetrofit(httpClient: OkHttpClient, baseUrl: String) = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxSingleCallAdapterFactory())
                .build()

    @Provides @Singleton @CountriesApi
    fun provideCountriesApiService(@CountriesApi retrofit: Retrofit) = retrofit.create(CountriesApiService::class.java)

    @Provides @Singleton
    fun provideWeatherApiService(@WeatherApi retrofit: Retrofit) = retrofit.create(WeatherApiService::class.java)

    @Provides @CountriesApi
    fun provideCountriesBaseUrl(): String = NetworkConstants.COUNTRIES_END_POINT

    @Provides @WeatherApi
    fun provideWeatherBaseUrl() = NetworkConstants.WETHER_END_POINT
}