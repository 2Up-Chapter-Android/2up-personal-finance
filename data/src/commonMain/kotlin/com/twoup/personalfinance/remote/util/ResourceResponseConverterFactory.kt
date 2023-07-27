package com.twoup.personalfinance.remote.util

import com.twoup.personalfinance.utils.data.HttpException
import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.UnknownException
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResourceResponseConverterFactory : Converter.Factory{

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if(typeData.typeInfo.type == Resource::class) {

            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                override suspend fun convert(response: HttpResponse): Any {
                    return try {
                        response.mapToResource<Any>(typeData)
                    } catch (ex: Throwable) {
                        Resource.error(UnknownException(msg = ex.message), null)
                    }
                }
            }
        }
        return null
    }

    private suspend inline fun <reified T> HttpResponse.mapToResource(typeData: TypeData): Resource<T> {
        return withContext(Dispatchers.Default) {
            try {
                if (status.isSuccess()) {
                    Resource.success(body(typeData.typeArgs.first().typeInfo))
                } else {
                    val errorBody = body<BaseErrorResponse>().data
                    Resource.error(HttpException(errorBody?.code, errorBody?.detail), null)
                }
            } catch (exception: Exception) {
                Resource.error(UnknownException(msg = exception.message), null)
            }
        }
    }
}

