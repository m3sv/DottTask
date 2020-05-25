package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.di.IO_CONTEXT
import com.m3sv.dotttask.shared.places.data.Venue
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@ApplicationScope
class GetVenue @Inject constructor(
    private val repository: PlacesRepository,
    @Named(IO_CONTEXT) private val context: CoroutineContext
) {
    suspend operator fun invoke(id: String): Venue = withContext(context) {
        val result = repository.cachedVenues.find { venue -> venue.id == id }
        // TODO technically this is impossible,
        //  since you can't click on a marker that does not exists, but
        //  returning Result probably makes better sense,
        //  this already takes too long to implement, so let's crash
        requireNotNull(result)
    }
}
