package com.m3sv.dotttask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.m3sv.dotttask.feature.details.DetailsViewModelImpl
import com.m3sv.dotttask.feature.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModelImpl::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModelImpl): ViewModel

}
