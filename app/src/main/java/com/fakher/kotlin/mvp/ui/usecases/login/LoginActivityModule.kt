package com.fakher.kotlin.mvp.ui.usecases.login

import com.fakher.kotlin.mvp.ui.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule(val activity: LoginActivity) {

    @ActivityScope
    @Provides
    fun providesLoginPresenter(): LoginPresenter = LoginPresenter(activity)
}