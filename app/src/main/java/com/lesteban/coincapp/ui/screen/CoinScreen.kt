package com.lesteban.coincapp.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.model.CoinCappAssetsResponse
import com.lesteban.coincapp.model.CoinCappResponse
import com.lesteban.coincapp.ui.components.AppLoader
import com.lesteban.coincapp.ui.components.BaseAppBar
import com.lesteban.coincapp.ui.navigation.EnumScreen
import com.lesteban.coincapp.utils.Constant
import com.lesteban.coincapp.utils.capsEachWord
import com.lesteban.coincapp.utils.debug
import com.lesteban.coincapp.utils.showToast
import com.lesteban.coincapp.viewmodel.CoinViewModel
import com.lesteban.coincapp.viewmodel.FavoriteViewmodel
import com.lesteban.coincapp.viewmodel.SettingsViewmodel

@Composable
fun CoinScreen (navController: NavController,message:String) {
    ScreenContent(navController,message)
}

@Composable
private fun ScreenContent(navController: NavController? = null, coin: String) {
    val viewModel = hiltViewModel<CoinViewModel>()
    val weatherDataState = viewModel.coinCappDataState.collectAsState()

    when (weatherDataState.value) {
        is ResponseApi.Loading -> AppLoader()

        is ResponseApi.Success -> {
            ScreenCoinContent(
                navController,
                (weatherDataState.value as ResponseApi.Success<CoinCappResponse>).data,
            )
        }

        is ResponseApi.Error -> {
            val message = (weatherDataState.value as ResponseApi.Error<CoinCappResponse>).message
            ScreenCoinContent(navController, errorMessage = message)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentCoinCapp(coin)
    }

}


@Composable
private fun ScreenCoinContent(
    navController: NavController?,
    coinCappResponse: CoinCappResponse? = null,
    errorMessage: String? = null
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<FavoriteViewmodel>()
    if (coinCappResponse?.data!!.name != null) viewModel.isFavorite(city = "Cardano")

    Scaffold(
        topBar = {
            BaseAppBar(
                title = coinCappResponse.data.name,
                isFavorite = viewModel.isFavoriteState.collectAsState().value,
                onActionButtonClicked = { enumAction ->
                    //navigateTo(navController, enumAction)
                },
                onAddRemoveFavorite = { action ->
                    debug("Favorite Action: ${action.name}")
                    //addRemoveFavorite(context, viewModel, action, coin)
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (coinCappResponse != null) {
                    WeatherContent(navController!!, coinCappResponse, context)
                } else {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center),
                        text = errorMessage?.capsEachWord() ?: "Unknown Error",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red.copy(alpha = 0.8f),
                        )
                    )
                }
            }
        }
    )
}

@Composable
private fun WeatherContent(navController: NavController, coinCappResponse: CoinCappResponse, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalDivider()
        Text(text = coinCappResponse.data.name)
        Text(text = coinCappResponse.data.rank)
        Text(text = coinCappResponse.data.symbol)
        Text(text = coinCappResponse.data.supply)
        Text(text = coinCappResponse.data.priceUsd)
    }
}