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

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    //First step, fetch data from the local cache
    val data = query().first()

    //If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {

        //Dispatch a message to the UI that you're doing some background work
        emit(Resource.loading(data))

        try {

            //make a networking call
            val resultType = fetch()

            //save it to the database
            saveFetchResult(resultType)

            //Now fetch data again from the database and Dispatch it to the UI
            query().map { Resource.success(it) }

        } catch (e: UnresolvedAddressException) {

            query().map { Resource.success(it) }
        } catch (e: IOException) {

            query().map { Resource.success(it) }
        } catch (throwable: Throwable) {

            //Dispatch any error emitted to the UI, plus data emmited from the Database
            query().map { Resource.error(UnknownException(throwable.message), it) }

        }

        //If should fetch returned false
    } else {
        //Make a query to the database and Dispatch it to the UI.
        query().map { Resource.success(it) }
    }

    //Emit the resource variable
    emitAll(resource)
}