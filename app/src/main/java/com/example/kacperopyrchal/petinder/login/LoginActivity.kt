package com.example.kacperopyrchal.petinder.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.DependencyInjector
import com.example.kacperopyrchal.petinder.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DependencyInjector.initialize()

        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.viewHolder, LoginFragment.newInstance(), "login_fragment")
                    .commitAllowingStateLoss()
        }

    }

}
