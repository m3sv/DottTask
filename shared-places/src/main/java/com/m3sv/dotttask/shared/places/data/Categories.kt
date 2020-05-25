package com.m3sv.dotttask.shared.places.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Categories(
    val id: String,
    val name: String,
    val pluralName: String,
    val shortName: String,
    val icon: Icon,
    val primary: Boolean
)
