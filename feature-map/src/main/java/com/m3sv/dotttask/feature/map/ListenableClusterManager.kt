package com.m3sv.dotttask.feature.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager

class ListenableClusterManager<T : ClusterItem> : ClusterManager<T> {

    private val onCameraIdle: () -> Unit

    constructor(
        context: Context?,
        map: GoogleMap?,
        onCameraIdle: () -> Unit
    ) : super(
        context,
        map
    ) {
        this.onCameraIdle = onCameraIdle
    }

    constructor(
        context: Context?,
        map: GoogleMap?,
        markerManager: MarkerManager?,
        onCameraIdle: () -> Unit
    ) : super(
        context,
        map,
        markerManager
    ) {
        this.onCameraIdle = onCameraIdle
    }

    override fun onCameraIdle() {
        super.onCameraIdle()
        onCameraIdle.invoke()
    }
}
