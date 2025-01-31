package com.lesteban.coincapp.di.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lesteban.coincapp.data.api.CoinCappAPI
import com.lesteban.coincapp.data.db.FavoriteDao
import com.lesteban.coincapp.data.repo.CoinCappRepository
import com.lesteban.coincapp.data.repo.CoinRepository
import com.lesteban.coincapp.data.repo.FavoriteRepository
import com.lesteban.coincapp.data.repo.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun coinCappRepository(coinCappAPI: CoinCappAPI) = CoinCappRepository(coinCappAPI)

    @Provides
    @ViewModelScoped
    fun favoriteRepository(favoriteDao: FavoriteDao) = FavoriteRepository(favoriteDao)

    @Provides
    @ViewModelScoped
    fun settingsRepository(dataStore: DataStore<Preferences>) = SettingsRepository(dataStore)

    @Provides
    @ViewModelScoped
    fun coinRepository(coinCappAPI: CoinCappAPI) = CoinRepository(coinCappAPI)


}