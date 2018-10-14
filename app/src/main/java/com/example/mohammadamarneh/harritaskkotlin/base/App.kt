package com.example.mohammadamarneh.harritaskkotlin.base

import android.app.Activity
import android.app.Application
import com.example.mohammadamarneh.harritaskkotlin.BuildConfig
import com.example.mohammadamarneh.harritaskkotlin.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector{
    @Inject
    lateinit var dispatchingAndroidInjectorActivity: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjectorActivity
}