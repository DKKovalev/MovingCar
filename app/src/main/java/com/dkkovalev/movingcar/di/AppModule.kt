package com.dkkovalev.movingcar.di

import com.dkkovalev.movingcar.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun providesContext() = app.context
}