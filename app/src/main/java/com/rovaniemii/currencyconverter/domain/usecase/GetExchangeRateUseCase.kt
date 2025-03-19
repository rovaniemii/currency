package com.rovaniemii.currencyconverter.domain.usecase

import com.rovaniemii.currencyconverter.data.model.ExchangeRate
import com.rovaniemii.currencyconverter.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val repository: ExchangeRateRepository,
) {
    fun execute(searchDate: String): Flow<Result<List<ExchangeRate>>> {
        return repository.getExchangeRates(searchDate)
    }
}