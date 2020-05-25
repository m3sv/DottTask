package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.places.data.Venue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetVenueTest {
    private lateinit var useCase: GetVenue

    private val repository = mock<PlacesRepository>()

    // TODO this should definitely be a rule/extension
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        useCase = GetVenue(repository, mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given empty cache when invoke then throw`() {
        runBlocking {
            whenever(repository.cachedVenues).thenReturn(listOf())

            useCase("")
        }
    }

    @Test
    fun `given cache with required value when invoke then return`() {
        runBlocking {
            val venueId = "1"
            val venue = Venue(
                id = venueId,
                name = "name",
                location = mock(),
                categories = listOf(),
                referralId = "",
                hasPerk = true
            )
            whenever(repository.cachedVenues).thenReturn(listOf(venue))

            assertEquals(venue, useCase(venueId))
        }
    }

}
