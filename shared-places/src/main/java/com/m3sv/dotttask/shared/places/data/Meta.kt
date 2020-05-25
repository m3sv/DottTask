package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    val code: Int,
    val requestId: String
)
