package com.lewisgarton.googlemapsproto

import android.Manifest
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.TextView
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted

val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

class EntryActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        requestPermissions(REQUIRED_PERMISSIONS)


    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if(perms.containsAll(REQUIRED_PERMISSIONS.toList())) {
            startMapsActivity()
        } else {
            setError()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun requestPermissions(permissions: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            startMapsActivity()
        } else {
            EasyPermissions.requestPermissions(
                    host = this,
                    rationale = "You must enable permissions to use this application, this can be done in settings",
                    requestCode = 1,
                    perms = *permissions
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) = setError()

    fun setError() {
        this.findViewById<TextView>(R.id.permissionsError).visibility =  VISIBLE
    }

    fun startMapsActivity() {
        startActivity(Intent(this, MapsActivity::class.java))
    }

}