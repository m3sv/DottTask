package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.di.IO_CONTEXT
import com.m3sv.dotttask.shared.places.data.LatLng
import com.m3sv.dotttask.shared.places.data.Venue
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@ApplicationScope
class PlacesRepository @Inject constructor(
    private val foursquareService: FoursquareService,
    @Named(IO_CONTEXT) private val context: CoroutineContext
) {
    private val venueChannel = BroadcastChannel<LatLng>(Channel.CONFLATED)

    private val venuesCache: MutableSet<Venue> = mutableSetOf()

    val cachedVenues: List<Venue>
        get() = venuesCache.toList()

    val venues: Flow<Result<Sequence<Venue>>> = venueChannel
        .asFlow()
        .distinctUntilChanged()
        .map { location ->
            Timber.d("Fetch venues for $location")

            val result = safeApiRequest(location)

            if (result != null)
                Result.success(result)
            else
                Result.failure(IllegalStateException("Failed to fetch venues"))
        }
        .flowOn(context)

    private suspend fun safeApiRequest(location: LatLng): Sequence<Venue>? = try {
        val apiResponse = foursquareService.search(location)

        if (apiResponse.meta.code == 200) {
            val result = apiResponse
                .response
                .venues
                .asSequence()

            venuesCache.addAll(result)

            result
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }

    fun searchLocation(location: LatLng) {
        venueChannel.offer(location)
    }
}
