package com.rovaniemii.currencyconverter.data.remote

import com.rovaniemii.currencyconverter.config.BaseAPIConstants
import com.rovaniemii.currencyconverter.data.model.ExchangeRateDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET(BaseAPIConstants.CURRENCY_CONVERTER_URL)
    suspend fun getCurrencyConverterData(
        @Query("authkey") authKey: String = BaseAPIConstants.AUTH_KEY,
        @Query("searchdate") searchDate: String,
        @Query("data") data: String = "AP01",
    ): Response<List<ExchangeRateDTO>>
}