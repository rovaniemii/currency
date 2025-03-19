package com.rovaniemii.currencyconverter.di

import com.rovaniemii.currencyconverter.config.BaseAPIConstants
import com.rovaniemii.currencyconverter.data.ExchangeRateRepositoryImpl
import com.rovaniemii.currencyconverter.data.remote.ExchangeRateApi
import com.rovaniemii.currencyconverter.domain.repository.ExchangeRateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseAPIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideExchangeRateApi(retrofit: Retrofit): ExchangeRateApi {
        return retrofit.create(ExchangeRateApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        exchangeRateApi: ExchangeRateApi,
    ): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(exchangeRateApi)
    }
}