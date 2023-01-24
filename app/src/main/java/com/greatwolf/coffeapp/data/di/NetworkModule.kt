package com.greatwolf.coffeapp.di

import com.greatwolf.coffeapp.data.api.BASE_URL
import com.greatwolf.coffeapp.data.api.CoffeeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCoffeeApi(): CoffeeApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CoffeeApi::class.java)
    }
}