package com.lewisgarton.googlemapsproto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.libraries.places.api.net.PlacesClient

class ViewModel(application: Application) : AndroidViewModel(application) {
    var places: PlacesClient? = null
}
