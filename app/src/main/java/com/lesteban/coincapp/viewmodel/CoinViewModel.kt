package com.lesteban.coincapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.data.repo.CoinRepository
import com.lesteban.coincapp.model.CoinCappResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinCappDataState = MutableStateFlow<ResponseApi<CoinCappResponse>>(ResponseApi.Loading())
    val coinCappDataState: StateFlow<ResponseApi<CoinCappResponse>> = _coinCappDataState

    fun fetchCurrentCoinCapp(coin: String)  {
        viewModelScope.launch {
            _coinCappDataState.value = ResponseApi.Loading()
            val result = coinRepository.fetchCoinCappCoin(coin)
            _coinCappDataState.value = result
        }
    }
}