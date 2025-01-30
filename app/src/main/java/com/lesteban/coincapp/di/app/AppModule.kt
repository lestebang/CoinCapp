package com.lesteban.coincapp.di.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.lesteban.coincapp.data.api.CoinCappAPI
import com.lesteban.coincapp.data.api.ErrorInterceptor
import com.lesteban.coincapp.data.db.FavoriteDao
import com.lesteban.coincapp.data.db.LocalDatabase
import com.lesteban.coincapp.utils.Constant
import com.lesteban.coincapp.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    fun retrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ErrorInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @AppScope
    fun coinCappAPI(retrofit: Retrofit): CoinCappAPI {
        return retrofit.create(CoinCappAPI::class.java)
    }


    @Provides
    @AppScope
    fun localDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            name = "local_db"
        ).fallbackToDestructiveMigrationFrom().build()
    }

    @Provides
    @AppScope
    fun favoriteDao(localDatabase: LocalDatabase): FavoriteDao {
        return localDatabase.favoriteDao()
    }

    @Provides
    @AppScope
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }



}