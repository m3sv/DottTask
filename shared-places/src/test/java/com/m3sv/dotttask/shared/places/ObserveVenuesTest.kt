package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.places.data.Location
import com.m3sv.dotttask.shared.places.data.Venue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveVenuesTest {
    private lateinit var useCase: ObserveVenues

    private val repository = mock<PlacesRepository>()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        useCase = ObserveVenues(repository, mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `when invoke then filter empty venues flow from repository`() = runBlocking {
        val nullVenue = Venue("", "", Location(lat = null, lng = null), listOf(), "", false)
        val venue = Venue("", "", Location(lat = 1.0, lng = 1.0), listOf(), "", false)

        val repositoryFlow = flowOf(Result.success(sequenceOf(venue, nullVenue)))
        whenever(repository.venues).thenReturn(repositoryFlow)

        val result = useCase().take(1).toList()[0].getOrNull()!!.toList()
        assertEquals(listOf(venue), result)
    }
}
