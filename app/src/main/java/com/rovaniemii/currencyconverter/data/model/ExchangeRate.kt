package com.rovaniemii.currencyconverter.data.model

/**
 * 환전 금액 = 원화 금액 ÷ 매매 기준 환율
 * 원화 금액 = 외화 금액 × 매매 기준 환율
 */
data class ExchangeRate(
    val currencyCode: String?, // 통화 코드
    val dealBaseRate: Double?, // 매매 기준 환율
)