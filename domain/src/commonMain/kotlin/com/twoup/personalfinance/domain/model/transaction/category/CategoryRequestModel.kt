package com.twoup.personalfinance.domain.model.transaction.category

import com.twoup.personalfinance.const.ConstDefaultValue
import com.twoup.personalfinance.domain.model.wallet.Category
import com.twoup.personalfinance.domain.model.wallet.Wallet
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequestModel(
    val name : String? = "",
    val category: String? = ""
)

