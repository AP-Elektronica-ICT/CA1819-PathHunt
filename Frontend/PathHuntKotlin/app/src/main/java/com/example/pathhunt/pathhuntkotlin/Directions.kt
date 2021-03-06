package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Directions(
    val geocoded_waypoints: Array<GeocodedWaypoint>,
    val routes: Array<Route>,
    val status: String
)
{
    class Deserializer3: ResponseDeserializable<Directions>{
        override fun deserialize(content: String): Directions? {
            return Gson().fromJson(content,Directions::class.java)
        }
    }
}

data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: Array<String>
)

data class Route(
    val bounds: Bounds,
    val copyrights: String,
    val legs: Array<Leg>,
    val overview_polyline: OverviewPolyline,
    val summary: String,
    val warnings: Array<Any>,
    val waypoint_order: Array<Any>
)

data class Bounds(
    val northeast: Northeast,
    val southwest: Southwest
)

data class Southwest(
    val lat: Double,
    val lng: Double
)

data class Northeast(
    val lat: Double,
    val lng: Double
)

data class OverviewPolyline(
    val points: String
)

data class Leg(
    val distance: Distance,
    val duration: Duration,
    val end_address: String,
    val end_location: EndLocation,
    val start_address: String,
    val start_location: StartLocation,
    val steps: Array<Step>,
    val traffic_speed_entry: Array<Any>,
    val via_waypoint: Array<Any>
)

data class Distance(
    val text: String,
    val value: Int
)

data class EndLocation(
    val lat: Double,
    val lng: Double
)

data class Duration(
    val text: String,
    val value: Int
)

data class Step(
    val distance: Distance,
    val duration: Duration,
    val end_location: EndLocation,
    val html_instructions: String,
    val maneuver: String,
    val polyline: Polyline,
    val start_location: StartLocation,
    val travel_mode: String
)

data class Polyline(
    val points: String
)

data class StartLocation(
    val lat: Double,
    val lng: Double
)