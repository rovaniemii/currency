package com.rovaniemii.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rovaniemii.currencyconverter.domain.usecase.GetExchangeRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
) : ViewModel() {
    // 환율
    private val _exchangeRate = MutableStateFlow<List<Pair<String, Double>>>(listOf())
    val exchangeRate: StateFlow<List<Pair<String, Double>>> = _exchangeRate

    init {
        val searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        getExchangeRateUseCase.execute(searchDate)
            .map { result ->
                result
                    .onSuccess { rates ->
                        _exchangeRate.value = rates
                            .mapNotNull {
                                val currency = it.currencyCode
                                val rate = it.dealBaseRate

                                if (currency != null && rate != null) {
                                    Pair(currency, rate)
                                } else {
                                    null
                                }
                            }
                    }
                    .onFailure {
                        // todo
                    }
            }
            .launchIn(viewModelScope)
    }
}