package com.rovaniemii.currencyconverter.presentation.ui.state

data class CurrencyUiState(
    val isLoading: Boolean = false, /* ProgressBar 로딩 상태 */
    val showErrorMassage: String? = null, /* SnackBar 표시 상태 */
)