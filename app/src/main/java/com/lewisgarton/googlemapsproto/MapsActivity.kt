package com.lewisgarton.googlemapsproto

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmadalin.easypermissions.EasyPermissions

class MapsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }
}