package com.lesteban.coincapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesteban.coincapp.data.api.ResponseApi
import com.lesteban.coincapp.data.repo.CoinCappRepository
import com.lesteban.coincapp.model.CoinCappAssetsResponse
import com.lesteban.coincapp.model.CoinCappResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinCappViewModel @Inject constructor(
    private val coinCappRepository: CoinCappRepository
): ViewModel() {

    private val _coinCappAssetsDataState = MutableStateFlow<ResponseApi<CoinCappAssetsResponse>>(ResponseApi.Loading())
    val coinCappAssetsDataState: StateFlow<ResponseApi<CoinCappAssetsResponse>> = _coinCappAssetsDataState

    fun fetchAssets()  {
        viewModelScope.launch {
            _coinCappAssetsDataState.value = ResponseApi.Loading()
            val result = coinCappRepository.fetchAssets()
            _coinCappAssetsDataState.value = result
        }
    }
}