package com.example.mohammadamarneh.harritaskkotlin.di

import com.example.mohammadamarneh.harritaskkotlin.ui.countryActivity.CountryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivitiesBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeCountryActivity(): CountryActivity
}
