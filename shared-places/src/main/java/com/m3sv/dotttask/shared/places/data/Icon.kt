package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Icon(
    val prefix: String,
    val suffix: String
)
