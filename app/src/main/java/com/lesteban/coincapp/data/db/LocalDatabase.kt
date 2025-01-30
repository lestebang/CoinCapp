package com.lesteban.coincapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lesteban.coincapp.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}