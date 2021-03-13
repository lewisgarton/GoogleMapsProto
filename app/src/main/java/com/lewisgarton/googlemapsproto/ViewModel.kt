package com.lewisgarton.googlemapsproto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.net.PlacesClient

//TODO: remove this and get users current location
val tempStartingLocation = LatLng(-34.0, 151.0)

class ViewModel(application: Application) : AndroidViewModel(application) {
    var places: PlacesClient? = null
    val currentLocation: MutableLiveData<LatLng> = MutableLiveData(tempStartingLocation)

    fun getSelectedLocation(): LatLng =
        if (currentLocation.value == null) {
            tempStartingLocation
        } else {
            currentLocation.value!!
        }

    fun setPointOfInterest(pointOfInterest: PointOfInterest?) {

    }

}
