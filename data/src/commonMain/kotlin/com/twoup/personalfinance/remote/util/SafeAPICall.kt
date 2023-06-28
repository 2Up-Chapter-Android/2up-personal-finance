package com.twoup.personalfinance.remote.util

import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

inline fun <reified T> safeApiCall(
    apiCall: () -> Result<T>,
): Result<T> = try {
    apiCall()
} catch (e: IOException) {
    Result.failure(NetworkException)
} catch (e: SerializationException) {
    Result.failure(SerializationError)
}