package com.m3sv.dotttask.feature.map

import com.m3sv.dotttask.shared.navigation.Router

sealed class MapRoute {
    data class Details(val id: String): MapRoute()
}

interface MapRouter : Router<MapRoute>
