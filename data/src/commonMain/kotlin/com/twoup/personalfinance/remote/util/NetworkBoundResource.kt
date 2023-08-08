package com.twoup.personalfinance.remote.util

import com.twoup.personalfinance.utils.data.Resource
import com.twoup.personalfinance.utils.data.UnknownException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <T> networkBoundResource(
    crossinline query: () -> Flow<T>,
    crossinline fetch: suspend () -> Resource<T>,
    crossinline saveFetchResult: suspend (Resource<T>) -> Unit,
    crossinline shouldFetch: (T) -> Boolean = { true }
) = flow {

    //First step, fetch data from the local cache
    val initQuery = query()
    val data = initQuery.first()

    //If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {

        //Dispatch a message to the UI that you're doing some background work
        emit(Resource.loading(data))

        try {

            //make a networking call
            val resultType = fetch()

            when {
                resultType.isSuccessful() -> {
                    //save it to the database
                    saveFetchResult(resultType)
                    //Now fetch data again from the database and Dispatch it to the UI
                    query().map { Resource.success(it) }
                }

                resultType.isError() -> {
                    //Dispatch any error emitted to the UI, plus data emitted from the Database
                    initQuery.map { Resource.error(resultType.error!!, it) }
                }

                resultType.isLoading() -> {
                    initQuery.map { Resource.loading(it) }
                }

                else -> {
                    initQuery.map { Resource.error(UnknownException("UnknownException"), it) }
                }
            }

        } catch (e: UnresolvedAddressException) {

            initQuery.map { Resource.success(it) }
        } catch (e: IOException) {

            initQuery.map { Resource.success(it) }
        } catch (throwable: Throwable) {

            //Dispatch any error emitted to the UI, plus data emmited from the Database
            initQuery.map { Resource.error(UnknownException(throwable.message), it) }

        }

        //If should fetch returned false
    } else {
        //Make a query to the database and Dispatch it to the UI.
        query().map { Resource.success(it) }
    }

    //Emit the resource variable
    emitAll(resource)
}