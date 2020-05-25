package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LabeledLatLngs(
    val label: String,
    val lat: Double,
    val lng: Double
)
