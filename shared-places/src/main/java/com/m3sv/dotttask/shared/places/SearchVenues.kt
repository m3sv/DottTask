package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.places.data.LatLng
import javax.inject.Inject

@ApplicationScope
class SearchVenues @Inject constructor(private val repository: PlacesRepository) {
    operator fun invoke(location: LatLng) {
        repository.searchLocation(location)
    }
}
