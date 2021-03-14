package com.lewisgarton.googlemapsproto

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.net.PlacesClient

//TODO: remove this and get users current location
val tempStartingLocation = LatLng(-34.0, 151.0)

class ViewModel(application: Application) : AndroidViewModel(application) {
    var places: PlacesClient? = null
    val currentLocation: MutableLiveData<LatLng> = MutableLiveData(tempStartingLocation)
    val locationClient = LocationServices.getFusedLocationProviderClient(application)

    fun getSelectedLocation(): LatLng =
        if (currentLocation.value == null) {
            tempStartingLocation
        } else {
            currentLocation.value!!
        }

    fun setPointOfInterest(pointOfInterest: PointOfInterest?) {

    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation() {
        val locationResult = locationClient.lastLocation
        locationResult.addOnCompleteListener {
            if (it.isSuccessful) {
                currentLocation.value = LatLng(it.result.latitude, it.result.longitude)
            }
        }
    }

}
