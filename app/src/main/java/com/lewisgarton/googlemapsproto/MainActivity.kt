package com.lewisgarton.googlemapsproto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.vmadalin.easypermissions.EasyPermissions

private const val PLACES_API_KEY = BuildConfig.API_KEY
private const val TAG = "Places API"

class MainActivity : AppCompatActivity() {

    private val placesClient by lazy {
        Places.initialize(applicationContext, PLACES_API_KEY)
        Places.createClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapsFragment = supportFragmentManager.findFragmentById(R.id.fragment)
            as MapsFragment

        mapsFragment.placesClient = placesClient


        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment



        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.latLng}, ${place.id}")
                val newPos = place.latLng

                if (newPos != null) {
                    mapsFragment.googleMap?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                        newPos,
                        15.0F
                    ))
                    mapsFragment.googleMap?.addMarker(
                        MarkerOptions()
                            .position(newPos)
                            .title("${place.name}"))
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }

        })




    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}