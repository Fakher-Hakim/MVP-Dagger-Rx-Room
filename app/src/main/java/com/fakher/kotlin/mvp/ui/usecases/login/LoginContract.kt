package com.fakher.kotlin.mvp.ui.usecases.login

import com.fakher.kotlin.mvp.ui.BasePresenter
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

interface LoginContract {

    interface View {

        fun setLoadingVisibility(visible: Boolean)

        fun signInSuccessful(account: GoogleSignInAccount?, googleSignInClient: GoogleSignInClient?)

        fun signInError(message: String)
    }

    interface Presenter : BasePresenter {

        fun connect()

        fun handleSignInResult(task: Task<GoogleSignInAccount>)
    }
}