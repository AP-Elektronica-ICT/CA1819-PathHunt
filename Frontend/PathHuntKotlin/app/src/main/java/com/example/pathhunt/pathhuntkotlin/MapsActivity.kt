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
import com.google.maps.android.PolyUtil
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.example.pathhunt.pathhuntkotlin.Locatie
import com.example.pathhunt.pathhuntkotlin.Locatie.Deserializer
import com.example.pathhunt.pathhuntkotlin.Geometry
import com.example.pathhunt.pathhuntkotlin.Result.Deserializer2
import com.example.pathhunt.pathhuntkotlin.Directions.Deserializer3
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
    private var walkpath: MutableList<List<LatLng>> = mutableListOf()
    private var locationUpdateState = false

    //used for google maps activity (add stuff like markers etc)
    private lateinit var mMap: GoogleMap
    //used  for locations
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient
    //private lateinit var lastLocation: Location
    //var alllocations: MutableList<Location> = mutableListOf()
    //this id is used as parameter for getlocations
    var straatnaam: String? = ""
    var geometrie: String = ""
    var geocodeoutput: String=""
    var geocodelat: String= ""
    var geocodelng: String=""
    var directionsteps: String=""




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
        //getAllLocations(locationId)
        straatnaam = prefs.nextStreet

        createLocationRequest()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?:return
                for(location in locationResult.locations){
                    var update: LatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(update, 20.0f  ))
                }
            }
        }
       //buildGeofencingRequest()

        //use fusedlocationclient from locationservices
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        geofencingClient = LocationServices.getGeofencingClient(this)


        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Log.d("currentlatlng", currentLatLng.toString())
                getGeoCoding()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20.0f))
            }
        }



    }

    //onmapready function (adds stuff to mapsactivity)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        /*val school = LatLng(51.2297882, 4.4149717)
        mMap.addMarker(MarkerOptions().position(school).title("Marker on the school"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(school))

        val mas = LatLng(51.229118, 4.4049659)
        mMap.addMarker(MarkerOptions().position(mas).title("Marker on MAS"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(MAS))

        val oevel = LatLng(51.46023539999999,4.4489466)
        mMap.addMarker(MarkerOptions().position(oevel).title("oevelen"))
        //val nieuwedestination = LatLng(${locations.latitude},${locations.longitude})
        //mMap.addMarker(MarkerOptions().position(nieuwedestination).title("New Destination"))*/

        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = false
        mMap.uiSettings.setAllGesturesEnabled(false)
        mMap.setOnMarkerClickListener(this)
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 14.0f))
        mMap.isMyLocationEnabled = true

        /*mMap.addPolyline(
            PolylineOptions().add(
                school,
                mas
                //,nieuwedestination
            ).width(10F)
                .color(Color.RED)
        )*/

    }



    override fun onBackPressed() {
        //disable back button
    }

    private fun buildGeofence()/*: Geofence?*/ {
        //val latitude = 51.46023539999999
        //val longitude = 4.4489466
        val latitude = geocodelat.toDouble()
        val longitude = geocodelng.toDouble()
        val loc = LatLng(latitude,longitude)
        this.mMap.addMarker(MarkerOptions().position(loc).title("Next location"))
        val radius = 30

        if(!geofenceList.isEmpty()){
            geofenceList.clear()
        }
        //geofenceList.removeAt(0)

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
        addGeofences()
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

    override fun onStop() {
        removeGeofences()
        geofenceList.clear()
        finish()
        super.onStop()
    }

    override fun onPause() {
        super.onPause()

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

    fun addGeofences(){
        geofencingClient?.addGeofences(getGeofencingRequest(), geofencePendingIntent)?.run{
            addOnSuccessListener {
                Log.d("geo", "succesfull addition")
            }
            addOnFailureListener{
                Log.d("geo", "failed mate")
            }
        }
    }

    fun removeGeofences(){
        geofencingClient?.removeGeofences(geofencePendingIntent)?.run {
            addOnSuccessListener {
                Log.d("GeoDel", "Del Succes!")
                Log.d("GeoDel", prefs.nextStreet)
            }
            addOnFailureListener {
                Log.d("GeoDel", "Failed")
            }
        }
    }

    //this function will be used to get more accurate coordinates because directions api needs geocoded points, which we didn't have in the DB before
    fun getGeoCoding() {
        var apicall = Api().urlGeocoding + straatnaam
        Log.d("apicall:", apicall)
        apicall.httpGet().responseObject(com.example.pathhunt.pathhuntkotlin.Result.Deserializer2()){ request, response, result ->
            //request.timeout(10000)
            val (geocodeoutput, err) = result
            //Log.d("geocodeoutput", geocodeoutput.toString())
            //Log.d("err", err.toString())
//            geocodeoutput?.forEach {result ->
                //Log.d("outputgeo: ", result.toString())
               // Log.d("result",result.get().results[0].geometry.location.toString())
//
//            }
            geocodelat = result.get().results[0].geometry.location.lat.toString()
            geocodelng = result.get().results[0].geometry.location.lng.toString()
            //Log.d("geocodelat", geocodelat)
            //Log.d("geocodelng", geocodelng)
            buildGeofence()
            getDirections()

        }

    }



    val path: MutableList<List<LatLng>> = ArrayList()

    //this url will be used to get directions from directions google maps api
    fun getDirections() {
        var apicall2 = Api().urlDirections + "origin=" + lastLocation.latitude + "," + lastLocation.longitude + "&destination=" + geocodelat + "," + geocodelng + "&waypoints=" + prefs.nextExtraStreet
        //var apicall2 = Api().urlDirections + "origin=" + lastLocation.latitude + "," + lastLocation.longitude + "&destination=51.46023539999999,4.4489466"
        Log.d("apicall2:", apicall2)
        apicall2.httpGet().responseObject(Directions.Deserializer3()) { request, response, result ->
            val (directionoutput, err) = result
            Log.d("directionoutput: ", directionoutput.toString())
            Log.d("err",err.toString())

//            directionsteps= result.get().routes[0].legs[0].steps[0].toString()
 //           Log.d("directionsteps", directionsteps)

            val route0 = result.get().routes[0];

            for(leg in route0.legs){
                for (step in leg.steps){
                    var poly = PolyUtil.decode(step.polyline.points)
                    walkpath.add(poly)
                    Log.d("Poly", poly.toString())
                }
            }

            var pathnr: Int = 0
            for (path in walkpath){
                Log.d("Path", "Loop")
                mMap!!.addPolyline(PolylineOptions().addAll(walkpath[pathnr]).color(Color.RED))
                //mMap!!.addMarker(MarkerOptions().position(walkpath[0][pathnr]).title("Path" + pathnr))
                pathnr++
            }
        }

    }
}

