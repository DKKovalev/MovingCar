package com.dkkovalev.movingcar.di

import com.dkkovalev.movingcar.MainActivity
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [MotionModule::class])
@ActivityScope
interface MotionComponent {
    fun inject(activity: MainActivity)
}