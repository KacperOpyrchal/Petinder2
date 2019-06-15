package com.example.kacperopyrchal.petinder.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.viewHolder, DetailsFragment.newInstance(), "details_fragment")
                    .commitAllowingStateLoss()
        }
    }
}
