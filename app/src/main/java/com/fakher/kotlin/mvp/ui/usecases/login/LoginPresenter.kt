package com.fakher.kotlin.mvp.ui.usecases.login

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginPresenter(val view: LoginActivity) : LoginContract.Presenter {

    private var googleSignInClient: GoogleSignInClient? = null

    private val TAG = LoginPresenter::class.java.simpleName

    override fun subscribe() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(view, gso)
    }

    override fun unSubscribe() {
        //Nothing to do!
    }

    override fun connect() {
        view.setLoadingVisibility(true)
        val signInIntent = googleSignInClient?.signInIntent
        view.startActivityForResult(signInIntent, view.RC_SIGN_IN)
    }

    override fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            view.signInSuccessful(account, googleSignInClient)

        } catch (e: ApiException) {

            Log.e(TAG, "signInResult:failed code=" + e.statusCode)
            e.message?.let { view.signInError(it) }
        } finally {
            view.setLoadingVisibility(false)
        }
    }
}