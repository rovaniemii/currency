package com.rovaniemii.currencyconverter.presentation.models

data class ExchangeRateViewData(
    val currencyName: String,
    val currencyCode: String,
    val dealBaseRate: Double,
)