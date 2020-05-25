package com.m3sv.dotttask.feature.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3sv.dotttask.shared.locationprovider.SharedLocationProvider
import com.m3sv.dotttask.shared.places.ObserveVenues
import com.m3sv.dotttask.shared.places.SearchVenues
import com.m3sv.dotttask.shared.places.data.LatLng
import com.m3sv.dotttask.shared.places.data.Venue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng as GoogleMapLatLng

class MapViewModel @Inject constructor(
    private val searchVenues: SearchVenues,
    private val sharedLocationProvider: SharedLocationProvider,
    observeVenues: ObserveVenues
) : ViewModel() {

    init {
        sendInitialLocation()

        viewModelScope.launch {
            observeVenues().collect(::handleVenuesResult)
        }
    }

    private val _cameraPosition: MutableLiveData<CameraUpdate> = MutableLiveData()

    val cameraPosition: LiveData<CameraUpdate> = _cameraPosition

    private val _venues = MutableLiveData<List<FoursquareItem>>()

    val venues: LiveData<List<FoursquareItem>> = _venues

    fun viewportChange(cameraUpdate: CameraUpdate) {
        setCameraPosition(cameraUpdate)
        fetchPlaces(cameraUpdate.position)
    }

    private fun sendInitialLocation() {
        sharedLocationProvider.getLastLocation(
            onSuccessListener = { location: Location? ->
                if (location != null) {
                    setCameraPosition(location.toCameraUpdate())
                } else {
                    // TODO deal with failure, show scrim view with try again
                }
            }, onFailureListener = {
                // TODO deal with failure, show scrim view with try again
            })
    }

    private fun fetchPlaces(location: LatLng) {
        searchVenues(location)
    }

    private suspend fun handleVenuesResult(result: Result<Sequence<Venue>>) {
        withContext(Dispatchers.IO) {
            if (result.isSuccess) {
                val venues = result
                    .getOrThrow()
                    .map { venue ->
                        val latitude = requireNotNull(venue.location.lat)
                        val longitude = requireNotNull(venue.location.lng)

                        FoursquareItem(
                            id = venue.id,
                            position = GoogleMapLatLng(latitude, longitude),
                            title = venue.name
                        )
                    }
                    .toList()

                _venues.postValue(venues)
            } else {
                Timber.e(result.exceptionOrNull())
                // TODO show scrim view with error and try again
            }
        }
    }

    private fun setCameraPosition(cameraUpdate: CameraUpdate) {
        _cameraPosition.value = cameraUpdate
    }
}
