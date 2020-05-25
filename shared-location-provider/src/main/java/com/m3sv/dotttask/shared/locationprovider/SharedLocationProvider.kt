package com.m3sv.dotttask.shared.locationprovider

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.m3sv.dotttask.shared.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class SharedLocationProvider @Inject constructor(context: Context) {
    private val locationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(
        onSuccessListener: (Location?) -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        locationProviderClient
            .lastLocation
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
}
