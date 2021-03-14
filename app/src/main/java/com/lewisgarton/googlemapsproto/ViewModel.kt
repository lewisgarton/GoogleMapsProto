package com.lewisgarton.googlemapsproto

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*

//TODO: remove this and get users current location
val tempStartingLocation = LatLng(-34.0, 151.0)

class ViewModel(application: Application) : AndroidViewModel(application) {
    val currentLocation: MutableLiveData<LatLng> = MutableLiveData(tempStartingLocation)
    val locationClient = LocationServices.getFusedLocationProviderClient(application)
    val locationImage: MutableLiveData<Bitmap> = MutableLiveData()

    val placesClient by lazy {
        Places.initialize(application, BuildConfig.GOOGLE_API_KEY)
        Places.createClient(application)
    }

    fun getSelectedLocation(): LatLng =
        if (currentLocation.value == null) {
            tempStartingLocation
        } else {
            currentLocation.value!!
        }

    fun updateHeaderImage(photoMetadata: PhotoMetadata?) {
        if(photoMetadata != null) {
            val photoRequest = FetchPhotoRequest.builder(photoMetadata)
                .build()
            placesClient.fetchPhoto(photoRequest)
                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                    locationImage.value = fetchPhotoResponse.bitmap
                }
        }
    }

    fun updateHeaderImage(placeID: String?) {
        if (placeID != null) {
            val placeRequest =
                FetchPlaceRequest.builder(placeID, listOf(Place.Field.NAME, Place.Field.PHOTO_METADATAS))
                    .build()
            placesClient.fetchPlace(placeRequest)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    Log.d("ZMAPS", "<${ response.place.name + response.place.photoMetadatas }>")
                    val photoMetadata: PhotoMetadata? = response.place.photoMetadatas?.get(0)
                    updateHeaderImage(photoMetadata)
                }.addOnFailureListener {
                    it.message?.let { it1 -> Log.d("MAPS", it1) }
                }
        }
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
