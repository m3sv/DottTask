package com.m3sv.dotttask.shared.places.data

data class LatLng(
    val latitude: Double,
    val longitude: Double
) {
    override fun toString(): String = "$latitude,$longitude"
}
