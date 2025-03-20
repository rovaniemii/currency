package com.rovaniemii.currencyconverter.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rovaniemii.currencyconverter.presentation.ui.input.InputAmountView
import com.rovaniemii.currencyconverter.presentation.viewmodel.CurrencyViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exchangeRate by viewModel.exchangeRate.collectAsStateWithLifecycle()
    val convertedAmount by viewModel.convertedAmount.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getExchangeRate()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        if (uiState.showErrorMassage == null) {
            item {
                InputAmountView(
                    modifier = Modifier.fillMaxWidth(),
                    initValue = convertedAmount?.toString() ?: "",
                    onValueChange = {
                        viewModel.setAmount(it.trim().toIntOrNull() ?: 0)
                    })
            }

            items(
                count = exchangeRate.size,
                key = { index ->
                    exchangeRate[index].hashCode()
                },
            ) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = exchangeRate[index].currencyName)
                    Text(text = exchangeRate[index].currencyCode)
                }
            }
        } else {
            uiState.showErrorMassage?.let { message ->
                item {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = message,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        // 로딩 상태
        if (uiState.isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
    }
}