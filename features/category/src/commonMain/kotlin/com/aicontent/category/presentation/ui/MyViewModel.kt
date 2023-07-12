package com.aicontent.category.presentation.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel() {
    private val _responseState = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val responseState: StateFlow<ResponseState> = _responseState.asStateFlow()

    fun performAction(token: String) {
        if (token.isBlank() || !isTokenValid(token)) {
            handleInvalidTokenResponse()
        } else {
            // Proceed with the request
            // Example: repository.makeRequest(token)
            // Update _responseState with the actual response
        }
    }

    private fun handleInvalidTokenResponse() {
        val invalidTokenResponse = ResponseState.Error(
            statusCode = 401,
            headers = emptyMap(),
            body = """
                {
                    "status": 401,
                    "timeStamp": "2023-02-05 22:41",
                    "data": "Unauthorized"
                }
            """.trimIndent()
        )
        _responseState.value = invalidTokenResponse
    }

    private fun isTokenValid(token: String): Boolean {
        // Perform token validation logic
        // Example: Check if the token is expired or matches a specific pattern
        return true // Replace with actual validation result
    }
}

sealed class ResponseState {
    object Loading : ResponseState()
    data class Success(val data: String) : ResponseState()
    data class Error(
        val statusCode: Int,
        val headers: Map<String, String>,
        val body: String
    ) : ResponseState()
}
