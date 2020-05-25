package com.m3sv.dotttask.di

import com.m3sv.dotttask.shared.di.ApplicationScope
import com.m3sv.dotttask.shared.places.FoursquareService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object FoursquareApiModule {

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.foursquare.com/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @JvmStatic
    @ApplicationScope
    fun provideFoursquareApi(retrofit: Retrofit): FoursquareService {
        return retrofit.create(FoursquareService::class.java)
    }

}
