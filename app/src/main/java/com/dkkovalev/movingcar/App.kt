package com.dkkovalev.movingcar

import android.app.Application
import android.content.Context
import com.dkkovalev.movingcar.di.AppComponent
import com.dkkovalev.movingcar.di.AppModule
import com.dkkovalev.movingcar.di.DaggerAppComponent

class App : Application() {


    var context: Context? = null
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component?.inject(this)

    }

    companion object {
        var component: AppComponent? = null
            private set
    }
}