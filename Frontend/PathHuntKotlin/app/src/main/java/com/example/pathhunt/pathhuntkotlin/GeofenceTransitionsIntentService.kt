package com.example.pathhunt.pathhuntkotlin

import android.app.IntentService
import android.app.Notification
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.location.GeofencingRequest

class GeofenceTransitionsIntentService : IntentService("GeoTrIntentService") {

    companion object {
        private const val LOG_TAG = "GeoTrIntentService"
    }

    override fun onHandleIntent(intent: Intent?) {
        // 1
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        // 2
        if (geofencingEvent.hasError()) {
            val errorMessage =
                geofencingEvent.errorCode
            Log.e(LOG_TAG, errorMessage.toString())
            return
        }
        // 3
        handleEvent(geofencingEvent)
    }

    private fun handleEvent(event: GeofencingEvent) {
        // 1
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            // 2
            val intent = Intent(this,QuestionActivity::class.java)
            startActivity(intent)

        }
    }


}