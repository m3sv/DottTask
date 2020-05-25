package com.m3sv.dotttask.di

import android.content.Context
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.map.MapFragment
import com.m3sv.dotttask.shared.di.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ViewModelModule::class,
        FoursquareApiModule::class,
        CoroutinesModule::class
    ]
)
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mapFragment: MapFragment)

    fun inject(detailsFragment: DetailsFragment)
}
