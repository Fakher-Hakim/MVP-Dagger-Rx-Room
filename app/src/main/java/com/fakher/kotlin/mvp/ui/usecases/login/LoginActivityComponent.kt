package com.fakher.kotlin.mvp.ui.usecases.login

import com.fakher.kotlin.mvp.ui.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [LoginActivityModule::class])
interface LoginActivityComponent {

    fun inject(loginActivity: LoginActivity)
}