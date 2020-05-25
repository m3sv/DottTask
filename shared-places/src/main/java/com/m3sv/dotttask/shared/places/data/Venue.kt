package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Venue(
    val id: String,
    val name: String,
    val location: Location,
    val categories: List<Categories>,
    val referralId: String,
    val hasPerk: Boolean
)
