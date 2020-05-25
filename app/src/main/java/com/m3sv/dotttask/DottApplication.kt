package com.m3sv.dotttask

import android.app.Application
import com.m3sv.dotttask.di.AppComponent
import com.m3sv.dotttask.di.DaggerAppComponent
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.details.DetailsInjector
import com.m3sv.dotttask.feature.map.MapFragment
import com.m3sv.dotttask.feature.map.MapInjector
import timber.log.Timber

class DottApplication : Application(), DetailsInjector, MapInjector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun inject(detailsFragment: DetailsFragment) {
        appComponent.inject(detailsFragment)
    }

    override fun inject(mapFragment: MapFragment) {
        appComponent.inject(mapFragment)
    }
}
