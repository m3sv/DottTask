package com.m3sv.dotttask.feature.map

import android.location.Location
import com.m3sv.dotttask.shared.places.data.LatLng

internal const val INITIAL_ZOOM = 16f

data class CameraUpdate(val position: LatLng, val zoom: Float = INITIAL_ZOOM)

internal fun Location.toCameraUpdate(zoom: Float = INITIAL_ZOOM): CameraUpdate =
    CameraUpdate(
        LatLng(
            latitude = latitude,
            longitude = longitude
        ),
        zoom = zoom
    )

