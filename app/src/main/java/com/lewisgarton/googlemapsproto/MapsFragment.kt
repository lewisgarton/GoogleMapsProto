package com.lewisgarton.googlemapsproto

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnPoiClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener{
    private lateinit var viewModel: ViewModel
    var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
        viewModel.currentLocation.observe(viewLifecycleOwner) { updateCameraPosition() }
        viewModel.getDeviceLocation()
    }

    fun moveCameraWithMarker(location: LatLng) {
        map?.let {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0F))
            it.clear()
            it.addMarker(MarkerOptions().position(location))
        }
    }

    fun moveCamera(location: LatLng) {
        map?.let {
            it.clear()
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0F))
        }
    }

    fun updateCameraPosition() {
        if(viewModel.currentLocation.value != null) {
            moveCamera(viewModel.currentLocation.value!!)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        with(googleMap) {
            map = this
            isMyLocationEnabled = true
            val initialLocation: LatLng = viewModel.getSelectedLocation()
            addMarker(MarkerOptions().position(initialLocation))

            moveCamera(CameraUpdateFactory.newLatLng(initialLocation))
        }

        googleMap.setOnPoiClickListener(this)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onPoiClick(pointOfInterest: PointOfInterest) {
        viewModel.placesClient
        Log.i("MAPS", "POI CLICK ${pointOfInterest.placeId}")
        viewModel.updateHeaderImage(pointOfInterest.placeId)
        moveCamera(pointOfInterest.latLng)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.i("MAPS", "MARKER CLICK $marker")
        return true
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }


}