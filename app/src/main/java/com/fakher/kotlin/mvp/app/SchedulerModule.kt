package com.fakher.kotlin.mvp.app

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    @Named(BACKGROUND_SCHEDULERS)
    fun backgroundScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named(MAIN_SCHEDULERS)
    fun mainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    companion object {
        const val MAIN_SCHEDULERS = "MAIN"
        const val BACKGROUND_SCHEDULERS = "BACKGROUND"
    }
}