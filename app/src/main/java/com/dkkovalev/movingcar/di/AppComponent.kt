package com.dkkovalev.movingcar.di

import com.dkkovalev.movingcar.App
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
}