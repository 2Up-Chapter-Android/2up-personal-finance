package com.twoup.personalfinance.local

import com.orhanobut.hawk.Hawk
import com.twoup.personalfinance.const.ConstDefaultValue

actual class SecureStorageWrapperImpl : SecureStorageWrapper {

    override fun saveValue(key: String, value: String) {
        Hawk.put(key, value)
    }

    override fun getValue(key: String): String? {
        return Hawk.get(key, ConstDefaultValue.DEFAULT_VALUE_STRING)
    }
}