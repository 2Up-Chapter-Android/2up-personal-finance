package com.twoup.personalfinance.local.di

import com.twoup.personalfinance.local.person.PersonLocalDataSource
import com.twoup.personalfinance.local.person.PersonLocalDataSourceImpl
import org.koin.dsl.module

fun localModule() = module {
    single<PersonLocalDataSource> { PersonLocalDataSourceImpl(get()) }
}