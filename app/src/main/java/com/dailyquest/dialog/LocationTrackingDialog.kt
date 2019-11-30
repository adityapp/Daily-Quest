package com.dailyquest.dialog

import android.content.Context
import com.dailyquest.R
import com.dailyquest.base.BaseDialog
import com.dailyquest.model.ChildrenLocationModel
import com.dailyquest.utils.Constants
import com.dailyquest.utils.beginWith
import com.dailyquest.utils.then
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_location_tracking.*

class LocationTrackingDialog(context: Context, private val childrenUid: String) :
    BaseDialog(context), OnMapReadyCallback {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun layoutId() = R.layout.dialog_location_tracking

    override fun setupView() {
        super.setupView()

        beginWith { setupOnClick() }
            .then { setupMap() }
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let { getLocation(it) }
    }

    private fun setupMap() {
        MapsInitializer.initialize(context)
        mapView.onCreate(onSaveInstanceState())
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private fun getLocation(map: GoogleMap) {
        map.isMyLocationEnabled = true
        LocationServices.getFusedLocationProviderClient(context).apply {
            lastLocation.addOnSuccessListener {
                map.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                            .target(LatLng(it.latitude, it.longitude))
                            .zoom(15.0f)
                            .build()
                    )
                )
            }
        }

        firebaseAuth.uid?.let { uid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .child(Constants.ANAK.toLowerCase()).child(childrenUid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showToast(p0.message)
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.getValue(ChildrenLocationModel::class.java)?.let {
                            val latLng = LatLng(it.latitude, it.longitude)
                            map.addMarker(MarkerOptions().position(latLng))
                        }
                    }
                })
        }
    }

    private fun setupOnClick() {
        b_ok.setOnClickListener { dismiss() }
    }
}