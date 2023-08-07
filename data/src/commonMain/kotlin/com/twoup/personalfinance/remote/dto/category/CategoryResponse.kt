package com.twoup.personalfinance.remote.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val status: Int? = 0,
    val status_message: String? = "",
    val timestamp: String? = "",
    val data : Data?
){
    @Serializable
    data class Data(
        val id : String? = "",
        val name : String? = "",
        val categoryId : String? = "",
        val userId : String? = ""
    )
}