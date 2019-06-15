package com.example.kacperopyrchal.petinder.registration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.viewHolder, RegistrationFragment.newInstance(), "registration_fragment")
                    .commitAllowingStateLoss()
        }
    }
}
