package com.example.kacperopyrchal.petinder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.kacperopyrchal.petinder.details.CITY
import com.example.kacperopyrchal.petinder.details.POINT_X
import com.example.kacperopyrchal.petinder.details.POINT_Y
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GpsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var x: Double = 0.0
    private var y: Double = 0.0
    private var city: String = "Krakow"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        x = intent.getDoubleExtra(POINT_X, 0.0)
        y = intent.getDoubleExtra(POINT_Y, 0.0)
        city = intent.getStringExtra(CITY)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(x, y)
        mMap.addMarker(MarkerOptions().position(location).title(city))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
