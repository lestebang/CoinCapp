package com.lesteban.coincapp.data.api

import com.lesteban.coincapp.model.CoinCappAssetsResponse
import com.lesteban.coincapp.model.CoinCappResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinCappAPI {
    @GET("v2/assets/{coin}")
    suspend fun getCoin(@Path("coin") searchCoin:String): Response<CoinCappResponse>

    @GET("v2/assets/")
    suspend fun getAssets(): Response<CoinCappAssetsResponse>
}