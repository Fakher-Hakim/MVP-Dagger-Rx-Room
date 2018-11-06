package com.fakher.kotlin.mvp.ui.usecases.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.fakher.kotlin.mvp.R
import com.fakher.kotlin.mvp.app.MyApplication
import com.fakher.kotlin.mvp.ui.BaseActivity
import com.fakher.kotlin.mvp.ui.usecases.posts.PostsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {

    val RC_SIGN_IN = 100

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initEvents()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    private fun initEvents() {
        sign_in_button.setOnClickListener {
            presenter.connect()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            presenter.handleSignInResult(task)
        }
    }

    override fun setLoadingVisibility(visible: Boolean) {
        login_progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun signInSuccessful(account: GoogleSignInAccount?, googleSignInClient: GoogleSignInClient?) {
        (application as MyApplication).createUserComponent(account, googleSignInClient)

        //Launch posts activity
        val intent = Intent(this, PostsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun signInError(message: String) {
        Log.e(LoginActivity::class.java.simpleName, "error while logging $message")
    }

    override fun injectDependency() {

        (application as MyApplication).appComponent
                ?.plus(LoginActivityModule(this))
                ?.inject(this)
    }
}