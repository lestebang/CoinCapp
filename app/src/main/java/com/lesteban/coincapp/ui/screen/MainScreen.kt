package  com.lesteban.coincapp.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.model.CoinCappAssetsResponse
import com.lesteban.coincapp.model.DataX
import com.lesteban.coincapp.model.Favorite
import com.lesteban.coincapp.ui.components.AppImage
import com.lesteban.coincapp.ui.components.AppLoader
import com.lesteban.coincapp.ui.components.BaseAppBar
import com.lesteban.coincapp.ui.components.EnumAction
import com.lesteban.coincapp.ui.components.EnumAppBarAction
import com.lesteban.coincapp.ui.navigation.EnumScreen
import com.lesteban.coincapp.utils.capsEachWord
import com.lesteban.coincapp.utils.debug
import com.lesteban.coincapp.utils.generateImageUrl
import com.lesteban.coincapp.utils.roundTo
import com.lesteban.coincapp.utils.showToast
import com.lesteban.coincapp.viewmodel.CoinCappViewModel
import com.lesteban.coincapp.viewmodel.FavoriteViewmodel
import com.lesteban.coincapp.viewmodel.SettingsViewmodel


@Composable
fun MainScreen(navController: NavController, coinName: String? = null) {
    val viewModel = hiltViewModel<CoinCappViewModel>()
    debug("Country: $coinName")

    val weatherDataState = viewModel.coinCappAssetsDataState.collectAsState()
    when (weatherDataState.value) {
        is ResponseApi.Loading -> AppLoader()

        is ResponseApi.Success -> {
            ScreenContent(
                navController,
                (weatherDataState.value as ResponseApi.Success<CoinCappAssetsResponse>).data
            )
        }

        is ResponseApi.Error -> {
            val message = (weatherDataState.value as ResponseApi.Error<CoinCappAssetsResponse>).message
            ScreenContent(navController, errorMessage = message)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAssets()
    }
}

@Composable
private fun ScreenContent(
    navController: NavController,
    coinCappAssetsResponse: CoinCappAssetsResponse? = null,
    errorMessage: String? = null
) {
    val coin = coinCappAssetsResponse?.data?.get(0)
    val title = if (coinCappAssetsResponse != null) "CoinCapp" else ""

    val context = LocalContext.current
    val viewModel = hiltViewModel<FavoriteViewmodel>()
    if (coin != null) viewModel.isFavorite(city = "Cardano")

    Scaffold(
        topBar = {
            BaseAppBar(
                title = title,
                isFavorite = viewModel.isFavoriteState.collectAsState().value,
                onActionButtonClicked = { enumAction ->
                    navigateTo(navController, enumAction)
                },
                onAddRemoveFavorite = { action ->
                    debug("Favorite Action: ${action.name}")
                    addRemoveFavorite(context, viewModel, action, coin)
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (coinCappAssetsResponse != null) {
                    WeatherContent(coinCappAssetsResponse)
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
private fun WeatherContent(coinCappAssetsResponse: CoinCappAssetsResponse) {
    val viewmodelSettings = hiltViewModel<SettingsViewmodel>()

    val weeklyWeatherList = coinCappAssetsResponse.data


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalDivider()
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = weeklyWeatherList) { weather ->
                WeatherItem(weather)
            }
        }
    }
}

@Composable
private fun WeatherItem(coin: DataX) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppImage(url = generateImageUrl(coin.symbol))
            Text(
                text = coin.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            )

            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                text = "$ ${coin.priceUsd.toDouble().roundTo(2).toString()}",
                style = TextStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                )
            )

            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                text = "% 24H ${coin.changePercent24Hr.toDouble().roundTo(2).toString()}",
                style = TextStyle(
                    color = Color.Gray ,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic
                )
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            thickness = 0.5.dp,
        )
    }
}

private fun navigateTo(navController: NavController?, enumAction: EnumAppBarAction) {
    val screenName: String = when (enumAction) {
        EnumAppBarAction.SEARCH -> EnumScreen.SEARCH_SCREEN.name
        EnumAppBarAction.FAVORITE -> EnumScreen.FAVORITE_SCREEN.name
        EnumAppBarAction.ABOUT -> EnumScreen.ABOUT_SCREEN.name
        EnumAppBarAction.SETTINGS -> EnumScreen.SETTINGS_SCREEN.name
    }
    navController?.navigate(screenName)
}

private fun addRemoveFavorite(
    context: Context,
    viewModel: FavoriteViewmodel,
    action: EnumAction,
    city: DataX?
) {
    if (city != null) {
        when (action) {
            EnumAction.ADD -> {
                viewModel.addFavorite(Favorite(coin = city.symbol, name = city.name))
                showToast(context, "City Added Into Favorite")
            }

            EnumAction.REMOVE -> {
                viewModel.removeFavorite(Favorite(coin = city.symbol, name = city.name))
                showToast(context, "City Removed From Favorite")
            }
        }
    }
}













