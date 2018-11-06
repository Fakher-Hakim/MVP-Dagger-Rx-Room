package com.fakher.kotlin.mvp.usermanager

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.Module
import dagger.Provides

@Module
class UserModule(var user: GoogleSignInAccount?, var googleSignInClient: GoogleSignInClient?) {

    @Provides
    @UserScope
    protected fun provideUser(): GoogleSignInAccount? {
        return user
    }

    fun logoutUser() {
        googleSignInClient?.signOut()
        googleSignInClient?.revokeAccess()
    }
}