package com.example.pathhunt.pathhuntkotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.example.pathhunt.pathhuntkotlin.Location
import com.example.pathhunt.pathhuntkotlin.Location.Deserializer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.doAsync


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val URL: String= "http://172.16.183.47:45455/api/locations"


    //this companion object will ask for permission to use locationservices
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getAllLocations(Api().urlLocations)
        setUpMap()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val school = LatLng(51.2297882, 4.4149717)
        mMap.addMarker(MarkerOptions().position(school).title("Marker on the school"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school))

        val mas = LatLng(51.2289238, 4.4026316)
        mMap.addMarker(MarkerOptions().position(mas).title("Marker on MAS"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(MAS))

        val steen = LatLng(51.2227238,4.390415)
        mMap.addMarker(MarkerOptions().position(steen).title("New Destination"))

        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 14.0f))
        mMap.isMyLocationEnabled = true

        mMap.addPolyline(
            PolylineOptions().add(
                school,
                mas,
                steen
            ).width(10F)
                .color(Color.RED)
        )
    }

    //checks for permission to search for finelocation (currentlocation)
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

     fun getAllLocations(URL: String){

        URL.httpGet().responseString { request, response, result ->
            when(result){
                is Result.Success -> {
                    val locations = result.get()
                    Log.d("Locations","$locations")
                }

                is Result.Failure-> {}
            }
        }
    }

    /*  private fun getDirectionURL(origin:LatLng,dest:LatLng):String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyAPwdADNSjGx-daM3Mx2HCpVNFfhlzf-lQ"

    }*/

    val path: MutableList<List<LatLng>> = ArrayList()
    var urlDirections: String =
        "https://maps.googleapis.com/maps/api/directions/json?origin=51.2297882,4.4149717&destination=51.2289238,4.4026316&key=AIzaSyAPwdADNSjGx-daM3Mx2HCpVNFfhlzf-lQ"

}
