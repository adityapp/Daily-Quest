package com.dailyquest.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LocationTrackingService : Service() {
    private var mLocationManager: LocationManager? = null
    private var pref: SessionManager? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    var mLocationListeners = arrayOf(
        LocationListener(LocationManager.GPS_PROVIDER),
        LocationListener(LocationManager.NETWORK_PROVIDER)
    )

    inner class LocationListener(provider: String) : android.location.LocationListener {
        private var mLastLocation: Location = Location(provider)

        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "${location.latitude} ${location.longitude}")
            mLastLocation.set(location)

            pref?.let {
                if (it.getRole() == Constants.ANAK) {
                    it.getParentUid()?.let { parentUid ->
                        firebaseAuth.uid?.let { uid ->
                            firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                                .child(Constants.ANAK.toLowerCase()).child(uid).updateChildren(
                                    mapOf(
                                        "latitude" to location.latitude,
                                        "longitude" to location.longitude
                                    )
                                )
                        }
                    }
                }
            }
        }

        override fun onProviderDisabled(provider: String) {
            //not implement needed
        }

        override fun onProviderEnabled(provider: String) {
            //not implement needed
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            //not implement needed
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        Log.d(TAG, "create service")
        pref = SessionManager(this)
        initializeLocationManager()
        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                mLocationListeners[1]
            )
        } catch (ex: SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }

        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL.toLong(), LOCATION_DISTANCE,
                mLocationListeners[0]
            )
        } catch (ex: SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }

    }

    override fun onDestroy() {
        Log.d(TAG, "destroy service")
        super.onDestroy()
        if (mLocationManager != null) {
            for (i in mLocationListeners.indices) {
                try {
                    mLocationManager!!.removeUpdates(mLocationListeners[i])
                } catch (ex: Exception) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex)
                }

            }
        }
    }

    private fun initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    companion object {
        private const val TAG = "LocationTrackingService"
        private const val LOCATION_INTERVAL = 3000
        private const val LOCATION_DISTANCE = 10f
    }
}