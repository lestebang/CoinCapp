package com.lesteban.coincapp.model

data class DataX(
    val changePercent24Hr: String,
    val explorer: String?,
    val id: String,
    val marketCapUsd: String,
    val maxSupply: String?,
    val name: String,
    val priceUsd: String,
    val rank: String,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String
)