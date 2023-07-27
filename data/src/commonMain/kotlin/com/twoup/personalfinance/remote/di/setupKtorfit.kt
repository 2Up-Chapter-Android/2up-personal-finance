package com.twoup.personalfinance.remote.di

import com.twoup.personalfinance.local.SecureStorageKey
import com.twoup.personalfinance.local.SecureStorageWrapper
import com.twoup.personalfinance.remote.util.ResourceResponseConverterFactory
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun setupKtorfit(baseUrl: String, enableNetworkLogs: Boolean) = module {
    single { provideKtorfit(get(), baseUrl) }
    single{ provideHttpClient(get(), enableNetworkLogs) }
}

private inline fun provideKtorfit(httpClient: HttpClient, baseUrl: String): Ktorfit {
    return Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .converterFactories(ResourceResponseConverterFactory()).build()
}

private inline fun provideHttpClient(secureStorageWrapper: SecureStorageWrapper, enableNetworkLogs: Boolean): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true; prettyPrint = true })
        }
        install(DefaultRequest) {
            headers.append(
                HttpHeaders.ContentType,
                ContentType.Application.Json.contentType + "/" + ContentType.Application.Json.contentSubtype
            )
            headers.append(
                HttpHeaders.Authorization,
                "Bearer ${secureStorageWrapper.getValue(SecureStorageKey.ACCESS_TOKEN)}"
            )
        }
        if (enableNetworkLogs){
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(tag = "HttpClient", message = message)
                    }
                }
            }.also {
                Napier.base(DebugAntilog())
            }
        }
    }
}