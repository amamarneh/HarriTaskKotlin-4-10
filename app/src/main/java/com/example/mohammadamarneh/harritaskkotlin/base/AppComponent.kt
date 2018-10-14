package com.example.mohammadamarneh.harritaskkotlin.base

import com.example.mohammadamarneh.harritaskkotlin.di.ActivitiesBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            ActivitiesBuilderModule::class,
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class]
)
interface AppComponent {
    fun inject(app: App)
}
