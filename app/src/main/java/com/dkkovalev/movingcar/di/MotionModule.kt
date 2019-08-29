package com.dkkovalev.movingcar.di

import com.dkkovalev.movingcar.MotionContract
import com.dkkovalev.movingcar.MotionPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MotionModule(private val view: MotionContract.View) {

    @Provides
    @ActivityScope
    fun providesMotionPresenter(): MotionContract.Presenter =
        MotionPresenterImpl(view)

    @Provides
    @ActivityScope
    fun providesView(): MotionContract.View = view
}