package com.example.pathhunt.pathhuntkotlin

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.app.IntentService
import android.app.PendingIntent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.Location


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
import com.example.pathhunt.pathhuntkotlin.Locatie
import com.example.pathhunt.pathhuntkotlin.Locatie.Deserializer
import com.example.pathhunt.pathhuntkotlin.Geometry
//import com.example.pathhunt.pathhuntkotlin.GeoCode.Deserializer2
import com.github.kittinunf.fuel.android.extension.jsonDeserializer
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
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
    private lateinit var lastLocation: Location
    private var geofenceList: MutableList<Geofence> = mutableListOf()
    private var locationUpdateState = false

    //used for google maps activity (add stuff like markers etc)
    private lateinit var mMap: GoogleMap
    //used  for locations
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient
    //private lateinit var lastLocation: Location
    //var alllocations: MutableList<Location> = mutableListOf()
    //this id is used as parameter for getlocations
    var locationId: Int = 1
    var straatnaam: String = ""
    var geometrie: String = ""
    var geocodeoutput: String=""




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


        createLocationRequest()
        buildGeofence()
       //buildGeofencingRequest()

        //use fusedlocationclient from locationservices
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geofencingClient = LocationServices.getGeofencingClient(this)
        geofencingClient?.addGeofences(getGeofencingRequest(), geofencePendingIntent)?.run{
            addOnSuccessListener {
                Log.d("geo", "succesfull addition")
            }
            addOnFailureListener{
                Log.d("geo", "failed mate")
            }
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Log.d("currentlatlng", currentLatLng.toString());
                getDirections()
            }}


    }

    //onmapready function (adds stuff to mapsactivity)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val school = LatLng(51.2297882, 4.4149717)
        mMap.addMarker(MarkerOptions().position(school).title("Marker on the school"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school))

        val mas = LatLng(51.229118, 4.4049659)
        mMap.addMarker(MarkerOptions().position(mas).title("Marker on MAS"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(MAS))

        val oevel = LatLng(51.46023539999999,4.4489466)
        mMap.addMarker(MarkerOptions().position(oevel).title("oevelen"))
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



    override fun onBackPressed() {
        //disable back button to avoid people going back to previous screens
        val intent = Intent(this,QuestionActivity::class.java)
        startActivity(intent)
    }

    private fun buildGeofence()/*: Geofence?*/ {
        //val latitude = 51.46023539999999
        //val longitude = 4.4489466
        val latitude = 51.431991
        val longitude = 4.436960
        val radius = 20

        //if (latitude != null && longitude != null && radius != null) {
        geofenceList.add(
                Geofence.Builder()
                    .setRequestId(1.toString())
                    .setCircularRegion(
                        latitude,
                        longitude,
                        radius.toFloat()
                    )
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build()
        )
       // }
    }

    private fun getGeofencingRequest(): GeofencingRequest{
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofenceList)
            .build()
    }



    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
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
       /* fusedLocationClient.lastLocation.addOnSuccessListener(this) { locatie ->
            if (locatie != null){
                lastLocation = locatie
                val currentLatLng = LatLng(locatie.latitude, locatie.longitude)

            }
        }*/

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        //update interval in millis
        locationRequest.interval = 100
        //update fastest interval in millis
        locationRequest.fastestInterval = 50
        //maximum wait time in millis
        locationRequest.maxWaitTime = 1000
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
        Api().urlLocations.httpGet().responseObject(Locatie.Deserializer()) { request, response, result ->
            val (locations, err) = result
            locations?.forEach { locatie ->
               // Log.d("Location: street", "${locatie.street}")
                straatnaam = locations[id].street
            }
            //Log.d("straatnaam",straatnaam)
            getGeoCoding()

        }



    }

    //this function will be used to get more accurate coordinates because directions api needs geocoded points, which we didn't have in the DB before
    fun getGeoCoding() {
        var apicall = Api().urlGeocoding + straatnaam
        Log.d("apicall:", apicall)
        apicall.httpGet().responseString { request, response, result ->
            val (geocodingoutput, err) = result
            Log.d("geocodingoutput: ", geocodingoutput.toString())
            Log.d("fuelerror: ", err.toString())


        }
       
    }



    val path: MutableList<List<LatLng>> = ArrayList()

    //this url will be used to get directions from directions google maps api
    fun getDirections() {
        var apicall2 = Api().urlDirections + lastLocation.latitude + "," + lastLocation.longitude
        Log.d("apicall2:", apicall2)
        apicall2.httpGet().responseString { request, response, result ->
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

