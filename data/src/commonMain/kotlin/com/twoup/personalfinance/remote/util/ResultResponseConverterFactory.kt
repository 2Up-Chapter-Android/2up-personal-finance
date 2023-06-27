package com.twoup.personalfinance.remote.util

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResultResponseConverterFactory : Converter.Factory{

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if(typeData.typeInfo.type == Result::class) {

            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                override suspend fun convert(response: HttpResponse): Any {
                    return try {
                        response.mapToResource<Any>(typeData)
                    } catch (ex: Throwable) {
                        Result.failure<CustomException>(UnknownException(msg = ex.message))
                    }
                }
            }
        }
        return null
    }

    private suspend inline fun <reified T> HttpResponse.mapToResource(typeData: TypeData): Result<T> {
        return withContext(Dispatchers.Default) {
            try {
                if (status.isSuccess()) {
                    Result.success(body(typeData.typeArgs.first().typeInfo))
                } else {
                    val errorBody = body<BaseErrorResponse>().data
                    Result.failure(HttpException(errorBody?.code, errorBody?.detail))
                }
            } catch (exception: Exception) {
                Result.failure(UnknownException(msg = exception.message))
            }
        }
    }
}

