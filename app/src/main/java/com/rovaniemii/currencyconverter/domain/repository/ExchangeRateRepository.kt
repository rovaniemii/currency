package com.rovaniemii.currencyconverter.domain.repository

import com.rovaniemii.currencyconverter.data.model.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface ExchangeRateRepository {
    fun getExchangeRates(searchDate: String): Flow<Result<List<ExchangeRate>>>
}