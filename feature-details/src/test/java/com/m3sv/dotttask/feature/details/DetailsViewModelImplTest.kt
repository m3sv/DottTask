package com.m3sv.dotttask.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.m3sv.dotttask.shared.common.util.getOrAwaitValue
import com.m3sv.dotttask.shared.places.GetVenue
import com.m3sv.dotttask.shared.places.data.Location
import com.m3sv.dotttask.shared.places.data.Venue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailsViewModelImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getVenue: GetVenue = mock()

    private val viewModelImpl: DetailsViewModelImpl = DetailsViewModelImpl(getVenue)

    // TODO this MUST be a rule/extension
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    // TODO for some reason last test throws a tantrum, comments this out
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
//    }

    @Test
    fun `given details when subscribe then present details`() = runBlocking {
        val venueName = "name"
        val venueAddress = "street"
        val lat = 1.0
        val lng = 1.0

        val venue = Venue(
            "",
            venueName,
            Location(address = venueAddress, lat = lat, lng = lng),
            listOf(),
            "",
            false
        )
        whenever(getVenue.invoke(any())).thenReturn(venue)
        viewModelImpl.id = ""

        val actual = viewModelImpl.details.getOrAwaitValue()

        val expected = listOf(
            Detail(venueName),
            Detail(venueAddress),
            Detail("${lat}:${lng}")
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `given empty address when subscribe then present details`() = runBlocking {
        val venueName = "name"
        val venueAddress = null
        val lat = 1.0
        val lng = 1.0

        val venue = Venue(
            "",
            venueName,
            Location(address = venueAddress, lat = lat, lng = lng),
            listOf(),
            "",
            false
        )

        whenever(getVenue.invoke(any())).thenReturn(venue)
        viewModelImpl.id = ""

        val actual = viewModelImpl.details.getOrAwaitValue()

        val expected = listOf(
            Detail(venueName),
            Detail(""),
            Detail("${lat}:${lng}")
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `given unknown location when subscribe then present details`() = runBlocking {
        val venueName = "name"
        val venueAddress = null
        val lat = null
        val lng = null

        val venue = Venue(
            "",
            venueName,
            Location(address = venueAddress, lat = lat, lng = lng),
            listOf(),
            "",
            false
        )

        whenever(getVenue.invoke(any())).thenReturn(venue)
        viewModelImpl.id = ""

        val actual = viewModelImpl.details.getOrAwaitValue()

        val expected = listOf(
            Detail(venueName),
            Detail(""),
            Detail("")
        )

        assertEquals(expected, actual)
    }
}
