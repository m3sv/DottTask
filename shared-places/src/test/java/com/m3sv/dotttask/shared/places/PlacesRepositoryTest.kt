package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.places.data.FoursquareResponse
import com.m3sv.dotttask.shared.places.data.Meta
import com.m3sv.dotttask.shared.places.data.Response
import com.m3sv.dotttask.shared.places.data.Venue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PlacesRepositoryTest {

    private lateinit var repository: PlacesRepository

    private val service: FoursquareService = mock()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = PlacesRepository(
            foursquareService = service,
            context = mainThreadSurrogate
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `given successful response when search location then result success`() = runBlocking {
        val venues = listOf<Venue>(mock(), mock(), mock())
        val successResponse = FoursquareResponse(Meta(200, ""), Response(venues, true))

        whenever(
            service.search(
                latLng = any(),
                clientId = any(),
                clientSecret = any(),
                version = any()
            )
        ).thenReturn(successResponse)

        repository.searchLocation(mock())

        val result = repository.venues.take(1).toList()[0]

        assertEquals(venues, result.getOrThrow().toList())
    }

    @Test
    fun `given 400 response when search location then result failure`() = runBlocking {
        val failedResponse = FoursquareResponse(Meta(400, ""), mock())

        whenever(
            service.search(
                latLng = any(),
                clientId = any(),
                clientSecret = any(),
                version = any()
            )
        ).thenReturn(failedResponse)

        repository.searchLocation(mock())

        val result = repository.venues.take(1).toList()[0]

        assertTrue(result.isFailure)
    }

    @Test
    fun `given failed request when search location then result success`() = runBlocking {
        val exception = IllegalArgumentException()

        whenever(
            service.search(
                latLng = any(),
                clientId = any(),
                clientSecret = any(),
                version = any()
            )
        ).thenThrow(exception)

        repository.searchLocation(mock())

        val result = repository.venues.take(1).toList()[0]

        assertTrue(result.isFailure)
    }
}
