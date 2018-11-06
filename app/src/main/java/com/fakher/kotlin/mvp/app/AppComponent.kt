package com.fakher.kotlin.mvp.app

import com.fakher.kotlin.mvp.data.NetworkModule
import com.fakher.kotlin.mvp.data.RepositoryModule
import com.fakher.kotlin.mvp.data.database.DatabaseModule
import com.fakher.kotlin.mvp.ui.usecases.login.LoginActivityComponent
import com.fakher.kotlin.mvp.ui.usecases.login.LoginActivityModule
import com.fakher.kotlin.mvp.usermanager.UserComponent
import com.fakher.kotlin.mvp.usermanager.UserModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class,
    DatabaseModule::class, RepositoryModule::class, SchedulerModule::class])
interface AppComponent {

    fun plus(loginModule: LoginActivityModule): LoginActivityComponent

    fun plus(userModule: UserModule): UserComponent
}