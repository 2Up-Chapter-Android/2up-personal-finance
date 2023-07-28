package com.twoup.personalfinance.domain.model.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseModel(
    val status: Int? = 0,
    val statusMessage: String? = "",
    val timestamp: String? = "",
    val data: CategoryInformation? = null
){
    @Serializable
    data class CategoryInformation(
        val id : String? = "",
        val name : String? = "",
        val categoryId : String? = "",
        val userId : String? = ""
    )
}