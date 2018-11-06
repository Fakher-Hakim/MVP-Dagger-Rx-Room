package com.fakher.kotlin.mvp.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(val application: Application) {

    @Provides
    fun providesApplication(): Context = application
}