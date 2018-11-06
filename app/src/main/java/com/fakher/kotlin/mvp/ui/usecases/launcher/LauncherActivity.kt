package com.fakher.kotlin.mvp.ui.usecases.launcher

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fakher.kotlin.mvp.app.MyApplication
import com.fakher.kotlin.mvp.ui.usecases.login.LoginActivity
import com.fakher.kotlin.mvp.ui.usecases.posts.PostsActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class LauncherActivity : AppCompatActivity() {

    private lateinit var application: MyApplication
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        application = applicationContext as MyApplication

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)

            application.createUserComponent(account, googleSignInClient)
        }

        if (application.userComponent == null) {
            //Must Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //User already logged
            val intent = Intent(this, PostsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    protected fun injectDaggerComponent() {
        //Nothing to be injected
        //In case login needs to check a token from the server,
        //we inject datasource to make api calls and check if the user is still logged.
    }
}