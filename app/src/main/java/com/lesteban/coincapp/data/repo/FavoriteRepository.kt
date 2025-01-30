package com.lesteban.coincapp.data.repo

import com.lesteban.coincapp.data.db.FavoriteDao
import com.lesteban.coincapp.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getFavorites():Flow<List<Favorite>> = favoriteDao.fetchFavorites().flowOn(Dispatchers.IO).conflate()
    fun isFavorite(coin: String) = favoriteDao.isFavorite(coin).flowOn(Dispatchers.IO).conflate()
    suspend fun addFavorite(favorite: Favorite) = favoriteDao.insert(favorite)
    suspend fun removeFavorite(favorite: Favorite) = favoriteDao.delete(favorite)
}