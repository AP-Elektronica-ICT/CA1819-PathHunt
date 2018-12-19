package com.example.pathhunt.pathhuntkotlin

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
//import android.location.Location


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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_maps.*
import org.jetbrains.anko.doAsync


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    //locationrequest, locationupdatestate & callback are used for locationupdates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    //geofencingclient is used for setting up the geofences
    //private lateinit var geofencingClient: GeofencingClient
    //used for google maps activity (add stuff like markers etc)
    private lateinit var mMap: GoogleMap
    //used  for locations
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private lateinit var lastLocation: Location
    //var alllocations: MutableList<Location> = mutableListOf()
    //this id is used as parameter for getlocations
    var locationId: Int = 1


    //this companion object will ask for permission to use locationservices & request settings check
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1

        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //call methods on this activity
        getAllLocations(locationId)
        getGeoCoding()
        getDirections()
        createLocationRequest()
        //setUpMap()

        //use fusedlocationclient & geofencingclient from locationservices
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //geofencingClient = LocationServices.getGeofencingClient(this)

        //soft solution to switch to questionsactivity
        btnQuestion.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

    }

    //onmapready function (adds stuff to mapsactivity)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val school = LatLng(51.2297882, 4.4149717)
        mMap.addMarker(MarkerOptions().position(school).title("Marker on the school"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school))

        val mas = LatLng(51.2289238, 4.4026316)
        mMap.addMarker(MarkerOptions().position(mas).title("Marker on MAS"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(MAS))

        //val nieuwedestination = LatLng(${locations.latitude},${locations.longitude})
        //mMap.addMarker(MarkerOptions().position(nieuwedestination).title("New Destination"))

        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 14.0f))
        mMap.isMyLocationEnabled = true

        mMap.addPolyline(
            PolylineOptions().add(
                school,
                mas
                //,nieuwedestination
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
        //fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,null)

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        //update interval in millis
        locationRequest.interval = 1
        //update fastest interval in millis
        locationRequest.fastestInterval = 0
        //high accuracy needed for locationupdates for geofencing
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //locationrequest
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // checks settings (location enabled etc)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // if locations is enabled, then update location & run setUpMap
        task.addOnSuccessListener {
            locationUpdateState = true
            setUpMap()
        }
        //if task fails show a dialog
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // exception
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    //get request for all locations coming from our db
    fun getAllLocations(id: Int) {
        Api().urlLocations.httpGet().responseObject(Location.Deserializer()) { request, response, result ->
            val (locations, err) = result
            locations?.forEach { location ->
                Log.d("Location: street", "${location.street}")
            }
        }
        /* Api().urlLocations/*+"${id}"*/.httpGet().responseString { request, response, result ->
            when(result){
                is Result.Success -> {
                    val location = result.get()
                    Log.d("Locations","$location")
                }

                is Result.Failure-> {}
            }
        }*/
    }

    //this function will be used to get more accurate coordinates because directions api needs geocoded points, which we didn't have in the DB before
    fun getGeoCoding() {
        Api().urlGeocoding.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val geocode = result.get()
                    Log.d("Geocode", "$geocode")
                }
                is Result.Failure -> {
                }
            }
        }
    }

    val path: MutableList<List<LatLng>> = ArrayList()

    //this url will be used to get directions from directions google maps api
    fun getDirections() {
        Api().urlDirections.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val directions = result.get()
                    //Log.d("Directions", "$directions")
                }
                is Result.Failure -> {

                }
            }
        }
    }
}

