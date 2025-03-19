package com.rovaniemii.currencyconverter.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rovaniemii.currencyconverter.presentation.viewmodel.CurrencyViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel = hiltViewModel(),
) {
    // 환율
    val exchangeRate by viewModel.exchangeRate.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(
            count = exchangeRate.size,
            key = { index ->
                exchangeRate[index].hashCode()
            },
        ) { index ->
            val currency = exchangeRate[index].first
            val rate = exchangeRate[index].second

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = currency)

                Text(text = rate.toString())
            }
        }
    }
}