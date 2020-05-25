package com.m3sv.dotttask.feature.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class FoursquareItem(
    val id: String,
    private val position: LatLng,
    private val title: String,
    private val snippet: String = ""
) : ClusterItem {
    override fun getSnippet(): String? = snippet
    override fun getTitle(): String? = title
    override fun getPosition(): LatLng = position
}
