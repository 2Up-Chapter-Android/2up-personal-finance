package com.twoup.personalfinance.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequestModel(
    val name : String? = "",
    val category: String? = ""
)