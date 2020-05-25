package com.m3sv.dotttask.di

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.di.IO_CONTEXT
import com.m3sv.dotttask.shared.di.MAIN_CONTEXT
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

@Module
object CoroutinesModule {

    @Provides
    @Named(IO_CONTEXT)
    @ApplicationScope
    fun provideIoContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Named(MAIN_CONTEXT)
    @ApplicationScope
    fun provideMainContext(): CoroutineContext = Dispatchers.Main
    
}
