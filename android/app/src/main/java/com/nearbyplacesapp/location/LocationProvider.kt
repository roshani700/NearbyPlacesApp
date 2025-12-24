package com.nearbyplacesapp.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.Manifest
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class LocationProvider(private val context: Context) {

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLocation(
        onSuccess: (Location) -> Unit,
        onError: (String) -> Unit
    ) {
        // Check for location permission
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onError("Permission denied")
            return
        }

        // Get last known location
        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onSuccess(location)
                } else {
                    onError("Location turned off or unavailable")
                }
            }
            .addOnFailureListener { exception ->
                onError("Failed to get location: ${exception.message}")
            }
    }
}
