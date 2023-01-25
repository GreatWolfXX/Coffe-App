package com.greatwolf.coffeeapp.data.di

import com.greatwolf.coffeeapp.data.api.BASE_URL
import com.greatwolf.coffeeapp.data.api.CoffeeApi
import com.greatwolf.coffeeapp.data.repository.CoffeeRepositoryImpl
import com.greatwolf.coffeeapp.domain.repository.CoffeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideCoffeeRetrofitApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCoffeeApi(retrofit: Retrofit): CoffeeApi =
        retrofit.create(CoffeeApi::class.java)

    @Provides
    fun provideCoffeeRepository(
        coffeeApi: CoffeeApi
    ): CoffeeRepository {
        return CoffeeRepositoryImpl(coffeeApi)
    }
}