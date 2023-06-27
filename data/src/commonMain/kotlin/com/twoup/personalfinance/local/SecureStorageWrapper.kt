package com.twoup.personalfinance.local

interface SecureStorageWrapper {
    fun saveValue(key: String, value: String)
    fun getValue(key: String): String?
}

expect class SecureStorageWrapperImpl: SecureStorageWrapper

object SecureStorageKey{
    const val ACCESS_TOKEN = "ACCESS_TOKEN"
    const val REFRESH_TOKEN = "REFRESH_TOKEN"
}