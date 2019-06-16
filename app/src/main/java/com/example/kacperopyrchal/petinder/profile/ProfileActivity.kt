package com.example.kacperopyrchal.petinder.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.viewHolder, ProfileFragment.newInstance(intent.extras.getString(LOCAL_NAME, "")), "profile_fragment")
                    .commitAllowingStateLoss()
        }
    }

}