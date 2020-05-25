package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.places.data.LatLng
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class SearchVenuesTest {
    private val repository = mock<PlacesRepository>()
    private val useCase = SearchVenues(repository)

    @Test
    fun `when invoke then trigger repository`() {
        val latLng = mock<LatLng>()
        useCase(latLng)
        verify(repository).searchLocation(latLng)
    }
}
