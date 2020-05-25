package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val address: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val labeledLatLngs: List<LabeledLatLngs>? = null,
    val distance: Int? = null,
    val postalCode: String? = null,
    val cc: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val formattedAddress: List<String>? = null
)
