package com.lesteban.coincapp.data.repo

import com.lesteban.coincapp.data.api.CoinCappAPI
import com.lesteban.coincapp.data.api.ExceptionApi
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.model.CoinCappAssetsResponse
import com.lesteban.coincapp.model.CoinCappResponse

class CoinCappRepository(
    private val coinCappAPI: CoinCappAPI
) {

    suspend fun fetchCoinCappCoin(coin: String): ResponseApi<CoinCappResponse>{
        return try {
            val response = coinCappAPI.getCoin(coin)
            ResponseApi.Success(data = response.body()!!)
        }catch (e: ExceptionApi){
            ResponseApi.Error(
                message = e.message?:"Unknown Error",
                errorCode = e.code
            )
        }catch (e: Exception) {
            ResponseApi.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }


    suspend fun fetchAssets(): ResponseApi<CoinCappAssetsResponse>{
        return try {
            val response = coinCappAPI.getAssets()
            ResponseApi.Success(data = response.body()!!)
        }catch (e: ExceptionApi){
            ResponseApi.Error(
                message = e.message?:"Unknown Error",
                errorCode = e.code
            )
        }catch (e: Exception) {
            ResponseApi.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }

}