package com.lesteban.coincapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lesteban.coincapp.ui.components.EnumUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStorePref: DataStore<Preferences>) {
    private val usdUnitKey = stringPreferencesKey("usd_unit_key")

    suspend fun changeTempUnit(unit: EnumUnit) {
        dataStorePref.edit { preferences ->
            preferences[usdUnitKey] = unit.name
        }
    }

    fun getTempUnit(): Flow<EnumUnit> {
        val enumUnit : Flow<EnumUnit> = dataStorePref.data.map { preferences ->
            val unitName = preferences[usdUnitKey] ?: EnumUnit.USD.name
            EnumUnit.valueOf(unitName)
        }
        return enumUnit
    }

}