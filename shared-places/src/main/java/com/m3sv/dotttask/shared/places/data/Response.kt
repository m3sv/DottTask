package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val venues: List<Venue>,
    val confident: Boolean
)
