package com.fakher.kotlin.mvp.app

import android.app.Application
import com.fakher.kotlin.mvp.data.NetworkModule
import com.fakher.kotlin.mvp.usermanager.UserComponent
import com.fakher.kotlin.mvp.usermanager.UserModule
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MyApplication : Application() {

    var userComponent: UserComponent? = null
    var appComponent: AppComponent? = null
    lateinit var userModule: UserModule

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()
    }

    fun createUserComponent(user: GoogleSignInAccount?, googleSignInClient: GoogleSignInClient?) {
        userModule = UserModule(user, googleSignInClient)
        userComponent = appComponent?.plus(userModule)
    }

    fun releaseUserComponent() {
        userModule.logoutUser()
        userComponent = null
    }
}