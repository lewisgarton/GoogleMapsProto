package com.lewisgarton.googlemapsproto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

class MapsActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        setContentView(R.layout.activity_maps)
    }

}