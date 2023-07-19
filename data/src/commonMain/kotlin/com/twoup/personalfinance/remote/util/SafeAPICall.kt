package com.twoup.personalfinance.remote.util

import com.twoup.personalfinance.utils.data.NetworkException
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.SerializationError
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

inline fun <reified T> safeApiCall(
    apiCall: () -> Resource<T>,
): Resource<T> = try {
    apiCall()
} catch (e: IOException) {
    Resource.error(NetworkException)
} catch (e: SerializationException) {
    Resource.error(SerializationError)
}