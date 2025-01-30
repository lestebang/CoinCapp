package com.lesteban.coincapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lesteban.coincapp.ui.components.AppRadioButton
import com.lesteban.coincapp.ui.components.BaseAppBar
import com.lesteban.coincapp.ui.components.EnumUnit
import com.lesteban.coincapp.ui.navigation.EnumScreen
import com.lesteban.coincapp.utils.debug
import com.lesteban.coincapp.viewmodel.SettingsViewmodel

@Composable
fun SettingsScreen(navController: NavController, viewmodel: SettingsViewmodel) {
    ScreenContent(navController, viewmodel)
}

@Composable
private fun ScreenContent(navController: NavController, viewmodel: SettingsViewmodel) {
    val radioOptionState = remember {
        mutableStateOf(EnumUnit.USD)
    }

    val savedUnit: EnumUnit = viewmodel.getUSDUnit().collectAsState(EnumUnit.USD).value
    radioOptionState.value = savedUnit
    debug("Saved Unit: ${savedUnit.name}")

    Scaffold (
        topBar = {
            BaseAppBar(
                title = "Settings",
                enumScreen = EnumScreen.SETTINGS_SCREEN,
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Temperature Units",
                    style = MaterialTheme.typography.titleMedium
                )
                AppRadioButton(
                    enumUnit = EnumUnit.USD,
                    radioOptionState.value == EnumUnit.USD,
                    onClickRadioBtn = {
                        radioOptionState.value = EnumUnit.USD
                        viewmodel.changeUSDUnit(EnumUnit.USD)
                    }
                )
                AppRadioButton(
                    enumUnit = EnumUnit.EUR,
                    radioOptionState.value == EnumUnit.EUR,
                    onClickRadioBtn = {
                        radioOptionState.value = EnumUnit.EUR
                        viewmodel.changeUSDUnit(EnumUnit.EUR)
                    }
                )
            }
        }
    )
}



















