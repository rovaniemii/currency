package com.rovaniemii.currencyconverter.data.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateDTO(
    // 조회 결과
    val result: Int = 0,

    // 통화 코드
    @SerializedName("cur_unit")
    val currencyCode: String? = null,

    // 매매 기준률
    @SerializedName("deal_bas_r")
    val dealBaseRate: String? = null,

    // 국가/통화명
    @SerializedName("cur_nm")
    val curName: String? = null,
)