package com.m3sv.dotttask.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.m3sv.dotttask.shared.places.GetVenue
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

interface DetailsViewModel {
    var id: String
    val details: LiveData<List<Detail>>
}

class DetailsViewModelImpl @Inject constructor(private val getVenue: GetVenue) : ViewModel(),
    DetailsViewModel {

    // TODO use assisted inject
    override lateinit var id: String

    override val details: LiveData<List<Detail>> by lazy(mode = NONE) {
        liveData {
            val details = getDetails()
            emit(details)
        }
    }

    private suspend fun getDetails(): List<Detail> {
        val venue = getVenue(id)
        val venueName = venue.name
        val venueAddress = venue.location.address ?: ""
        val lat = venue.location.lat
        val lng = venue.location.lng

        val venueLatLng = if (lat != null && lng != null)
            "${venue.location.lat}:${venue.location.lng}"
        else
            ""

        return listOf(
            Detail(venueName),
            Detail(venueAddress),
            Detail(venueLatLng)
        )
    }
}
