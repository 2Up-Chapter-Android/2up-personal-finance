package com.twoup.personalfinance.remote.util

import com.twoup.personalfinance.remote.dto.category.CategoryResponse
import com.twoup.personalfinance.utils.data.NetworkException
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.SerializationError
import com.twoup.personalfinance.utils.data.UnknownException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

inline fun <reified T> safeApiCall(
    apiCall: () -> Result<CategoryResponse>,
): Resource<T> = try {
    apiCall()
} catch (e: IOException) {
    Resource.error(NetworkException)
} catch (e: UnresolvedAddressException) {
    Resource.error(NetworkException)
} catch (e: SerializationException) {
    Resource.error(SerializationError)
} catch (e: Exception) {
    Resource.error(UnknownException(e.message))
}