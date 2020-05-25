package com.m3sv.dotttask.feature

import android.app.Application
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.details.DetailsInjector

class DetailsApp: Application(), DetailsInjector {
    override fun inject(detailsFragment: DetailsFragment) {
        // TODO setup test dagger factory here
    }
}
