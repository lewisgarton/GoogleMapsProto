package com.lewisgarton.googlemapsproto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.libraries.places.api.Places

private const val PLACES_API_KEY = BuildConfig.API_KEY

class MainActivity : AppCompatActivity() {

    private val placesClient by lazy {
        Places.initialize(applicationContext, PLACES_API_KEY)
        Places.createClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}