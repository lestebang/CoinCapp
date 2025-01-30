package com.lesteban.coincapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlin.math.pow
import kotlin.math.roundToInt


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.PREFERENCE_NAME)

fun String.capsFirstLetter(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

fun String.capsEachWord(): String {
    return  this.split(" ")
        .joinToString(" ") { it.lowercase().replaceFirstChar {char -> char.uppercase() } }
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}