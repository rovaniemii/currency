package com.rovaniemii.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rovaniemii.currencyconverter.domain.usecase.GetExchangeRateUseCase
import com.rovaniemii.currencyconverter.presentation.models.ExchangeRateViewData
import com.rovaniemii.currencyconverter.presentation.ui.state.CurrencyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CurrencyUiState())
    val uiState = _uiState.asStateFlow()

    // 환율
    private val _exchangeRate = MutableStateFlow<List<ExchangeRateViewData>>(listOf())
    val exchangeRate: StateFlow<List<ExchangeRateViewData>> = _exchangeRate

    // 환전을 원하는 금액 (원화) : 사용자 입력
    private val _convertedAmount = MutableStateFlow<Int?>(null)
    val convertedAmount: StateFlow<Int?> = _convertedAmount

    fun getExchangeRate() {
        val searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        getExchangeRateUseCase.execute(searchDate)
            .map { result ->
                _uiState.update { it.copy(isLoading = true) }

                result
                    .onSuccess { rates ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                showErrorMassage = null,
                            )
                        }
                        _exchangeRate.value = rates
                            .map {
                                ExchangeRateViewData(
                                    currencyName = it.currencyName,
                                    currencyCode = it.currencyCode,
                                    dealBaseRate = it.dealBaseRate,
                                )
                            }
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                showErrorMassage = getErrorMessage(exception),
                            )
                        }
                    }
            }
            .launchIn(viewModelScope)
    }

    fun setAmount(amount: Int) {
        _convertedAmount.value = amount
    }

    private fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is SocketTimeoutException -> "서버 응답이 지연되고 있습니다. 잠시 후 다시 시도해 주세요."
            is IOException -> "네트워크 연결에 문제가 발생했습니다. 인터넷 연결을 확인해 주세요."
            is HttpException -> {
                when (exception.code()) {
                    404 -> "요청한 데이터를 찾을 수 없습니다."
                    500 -> "서버에 문제가 발생했습니다. 잠시 후 다시 시도해 주세요."
                    else -> "서버 오류가 발생했습니다. 나중에 다시 시도해 주세요."
                }
            }

            else -> "알 수 없는 오류가 발생했습니다. 다시 시도해 주세요."
        }
    }
}