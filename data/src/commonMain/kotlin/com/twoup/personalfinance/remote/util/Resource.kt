package com.twoup.personalfinance.remote.util

data class Resource<out T>(val status: Status, val data: T?, val error: CustomException?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: CustomException, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccessful() = status == Status.SUCCESS

    fun isLoading() = status == Status.LOADING

    fun isError() = status == Status.ERROR
}

inline fun <X, Y> Resource<X>.map(transform: (X?) -> Y): Resource<Y> = Resource(status, transform(data), error)

fun <T> Resource<T>.fold(onSuccess: (T) -> Unit, onFailure: (CustomException) -> Unit, onLoading: () -> Unit = {}) {
    when {
        this.isSuccessful() -> onSuccess(data!!)
        this.isError() -> onFailure(error!!)
        this.isLoading() -> onLoading()
    }
}

inline fun <reified T> Result<T>.toResource(): Resource<T> {
    return when {
        this.isSuccess -> Resource.success(getOrNull()!!)
        this.isFailure -> {
            val exception = exceptionOrNull()
            if (exception is CustomException) Resource.error(exception)
            else Resource.error(UnknownException(exception?.message ?: "Unknown error"))
        }
        else -> Resource.loading()
    }
}