package com.lewisgarton.googlemapsproto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.observe
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
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

    private lateinit var image: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        image = activity?.findViewById(R.id.imageView)!!
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.locationImage.observeForever {
            image.setImageBitmap(viewModel.locationImage.value)
        }



        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                val newPos = place.latLng
                if (newPos != null) {
                    mapsFragment.moveCameraWithMarker(newPos)
                    viewModel.updateHeaderImage(place.photoMetadatas?.firstOrNull())
                }
            }

            override fun onError(status: Status) {
            }
        })
    }

}