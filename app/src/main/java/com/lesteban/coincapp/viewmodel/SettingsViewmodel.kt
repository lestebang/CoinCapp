package com.lesteban.coincapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesteban.coincapp.data.repo.SettingsRepository
import com.lesteban.coincapp.ui.components.EnumUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewmodel @Inject constructor(
    private val settingsRepository: SettingsRepository
): ViewModel() {

    fun changeUSDUnit(unit: EnumUnit) {
        viewModelScope.launch {
            settingsRepository.changeTempUnit(unit)
        }
    }

    fun getUSDUnit(): Flow<EnumUnit> {
        return settingsRepository.getTempUnit()
    }

}