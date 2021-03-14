package com.lewisgarton.googlemapsproto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class Fragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private val mapsFragment: MapsFragment
    get() =
        childFragmentManager.findFragmentById(R.id.mapFragment) as MapsFragment

    private val autocompleteFragment
    get() =
        childFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        val placesClient by lazy {
            context?.let { Places.initialize(it, BuildConfig.GOOGLE_API_KEY) }
            context?.let { Places.createClient(it) }
        }
        viewModel.places = placesClient

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val newPos = place.latLng
                if (newPos != null) {
                    mapsFragment.moveCameraWithMarker(newPos)
                }
            }

            override fun onError(status: Status) {
            }



        })
    }

}