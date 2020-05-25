package com.m3sv.dotttask

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.map.MapFragment
import com.m3sv.dotttask.feature.map.MapRoute
import com.m3sv.dotttask.feature.map.MapRouter
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


private const val RC_FINE_LOCATION_PERMISSION = 21

class ContainerActivity : AppCompatActivity(), MapRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = AppFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        if (savedInstanceState == null)
            startMapScreen()
    }

    @AfterPermissionGranted(RC_FINE_LOCATION_PERMISSION)
    private fun startMapScreen() {
        if (hasLocationPermission()) {
            val mapFragment = MapFragment()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .commit()
        } else {
            requestLocationPermission()
        }
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.location_rationale),
            RC_FINE_LOCATION_PERMISSION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun navigateTo(route: MapRoute) {
        when (route) {
            is MapRoute.Details -> showDetailsScreen(route.id)
        }
    }

    private fun showDetailsScreen(id: String) {
        replaceFragment<DetailsFragment>(
            arguments = DetailsFragment.arguments(id),
            addToBackStack = true
        ) { }
    }

    private inline fun <reified F : Fragment> replaceFragment(
        arguments: Bundle?,
        addToBackStack: Boolean = false,
        transactionAction: (FragmentTransaction) -> Unit
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, F::class.java, arguments, null)
            .apply(transactionAction)
            .apply { if (addToBackStack) addToBackStack(null) }
            .commit()
    }
}

