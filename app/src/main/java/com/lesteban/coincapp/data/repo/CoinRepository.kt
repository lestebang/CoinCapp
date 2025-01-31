package com.lesteban.coincapp.data.repo

import com.lesteban.coincapp.data.api.CoinCappAPI
import com.lesteban.coincapp.data.api.ExceptionApi
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.model.CoinCappResponse

class CoinRepository (
    private val coinCapApi: CoinCappAPI
){
    suspend fun fetchCoinCappCoin(coin: String): ResponseApi<CoinCappResponse>
    {
        return try {
            val response = coinCapApi.getCoin(coin)
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
