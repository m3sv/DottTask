package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.di.IO_CONTEXT
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@ApplicationScope
class ObserveVenues @Inject constructor(
    private val repository: PlacesRepository,
    @Named(IO_CONTEXT) private val context: CoroutineContext
) {
    operator fun invoke() = repository
        .venues
        .map { result ->
            var mappedResult = result

            if (result.isSuccess) {
                val mappedData = result
                    .getOrThrow()
                    .asSequence()
                    .filter { venue -> venue.location.lat != null && venue.location.lng != null }
                mappedResult = Result.success(mappedData)
            }

            mappedResult
        }
        .flowOn(context)
}
