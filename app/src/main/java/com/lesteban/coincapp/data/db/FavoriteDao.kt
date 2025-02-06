package com.lesteban.coincapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lesteban.coincapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    fun fetchFavorites(): Flow<List<Favorite>>

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE name = :coin)")
    fun isFavorite(coin: String): Flow<Boolean>
}