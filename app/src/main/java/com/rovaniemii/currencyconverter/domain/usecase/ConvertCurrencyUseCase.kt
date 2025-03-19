package com.rovaniemii.currencyconverter.domain.usecase

class ConvertCurrencyUseCase {
    fun execute(amount: Int, rate: Double): Double {
        return amount * rate
    }
}