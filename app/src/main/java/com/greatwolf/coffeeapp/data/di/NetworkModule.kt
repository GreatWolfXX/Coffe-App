package com.greatwolf.coffeeapp.data.di

import com.google.firebase.auth.FirebaseAuth
import com.greatwolf.coffeeapp.common.Constants.BASE_URL
import com.greatwolf.coffeeapp.data.api.CoffeeApi
import com.greatwolf.coffeeapp.data.repository.RepositoryImpl
import com.greatwolf.coffeeapp.domain.repository.Repository
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
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideCoffeeRepository(
        coffeeApi: CoffeeApi,
        firebaseAuth: FirebaseAuth
    ): Repository {
        return RepositoryImpl(firebaseAuth, coffeeApi)
    }
}