package com.m3sv.dotttask.feature.map

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.m3sv.dotttask.shared.common.util.observeOnce
import com.m3sv.dotttask.shared.places.data.LatLng
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng as GoogleMapLatLng

class MapFragment : SupportMapFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var clusterManager: ListenableClusterManager<FoursquareItem>

    private var googleMap: GoogleMap? = null

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((requireActivity().applicationContext) as MapInjector).inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get()
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.apply {
            styleMap()
            initClusterManager()
            viewModel.venues.observe(viewLifecycleOwner, Observer { items ->
                clusterManager.addItems(items)
                clusterManager.cluster()
            })
            viewModel.cameraPosition.observeOnce(viewLifecycleOwner) { centerCameraAtPosition(it) }
        }
    }

    private fun GoogleMap.styleMap() {
        setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
        mapType = GoogleMap.MAP_TYPE_NORMAL
        isMyLocationEnabled = true
        uiSettings.isMyLocationButtonEnabled = true
    }

    private fun GoogleMap.initClusterManager() {
        clusterManager = ListenableClusterManager<FoursquareItem>(
            context = requireContext(),
            map = this,
            onCameraIdle = {
                viewModel.viewportChange(
                    CameraUpdate(
                        position = LatLng(
                            cameraPosition.target.latitude,
                            cameraPosition.target.longitude
                        ),
                        zoom = cameraPosition.zoom
                    )
                )
            }
        ).apply {
            setOnCameraIdleListener(this)
            setOnMarkerClickListener(this)
            setOnClusterItemClickListener { item ->
                (requireActivity() as MapRouter).navigateTo(MapRoute.Details(item.id))
                true
            }
        }
    }

    private fun GoogleMap.centerCameraAtPosition(cameraUpdate: CameraUpdate) {
        val position = cameraUpdate.position

        moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                GoogleMapLatLng(
                    position.latitude,
                    position.longitude
                ),
                cameraUpdate.zoom
            )
        )
    }
}
