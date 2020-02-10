package com.k8596.ownproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.recyclerview_item.*
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val message = intent.getStringExtra(EXTRA_MESSAGE)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        val data = intent.getStringExtra("latlng")
        val params  = data.split(",")
        val lat = params[0]
        val lng = params[1]
        val title = params[2]

            mMap = googleMap
            val mark = LatLng(lat.toDouble(), lng.toDouble())
            mMap.addMarker(MarkerOptions().position(mark).title(title).snippet(lat+","+lng))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mark))
        }

}
