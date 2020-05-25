package com.m3sv.dotttask.shared.places

import com.m3sv.dotttask.shared.places.data.FoursquareResponse
import com.m3sv.dotttask.shared.places.data.LatLng
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareService {

    @GET("venues/search")
    suspend fun search(
        @Query("ll") latLng: LatLng,
        // TODO put this stuff in an interceptor
        @Query("client_id") clientId: String = "HJF3LO10GOAXJFF45ICNX15UZQVRSR1TJVZ5LW4PIOW5GWSZ",
        @Query("client_secret") clientSecret: String = "2LXBRCK3L0R0WIMGC1ZFWCTCPPIZDZDFQTW2X3S3XT1EGIYC",
        @Query("v") version: String = "20200519"
    ): FoursquareResponse

}
