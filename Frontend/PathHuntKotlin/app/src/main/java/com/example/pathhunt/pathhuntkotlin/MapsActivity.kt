package com.example.pathhunt.pathhuntkotlin

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.beust.klaxon.Klaxon

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import java.net.URL

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationId : Int = 1
    var location: Location? =null

    //this companion object will ask for permission to use locationservices
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setUpMap()
        getLocation(locationId)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val school = LatLng(51.2297882, 4.4149717)
        mMap.addMarker(MarkerOptions().position(school).title("Marker on the school"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school))

        /*You can choose a different maptypes with this fct*/
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        //sets zoomcontrols
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 14.0f))

        //this shows current location on map, uses the checkselfpermission to check if location services are allowed, if not allowed currentlocation will not show
        mMap.isMyLocationEnabled = true

    }

    //checks for permission to search for finelocation (currentlocation)
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    private fun getLocation(id:Int){
        doAsync {
            val result = URL("http://192.168.1.62:45455/api/locations/$id").readText()
            location = Klaxon ()
                .parse<Location>(result)
        }
    }
}
