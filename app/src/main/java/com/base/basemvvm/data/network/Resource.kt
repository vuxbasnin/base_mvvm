package com.base.basemvvm.data.network

/**
 * getting state of network call
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String = "") {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null)
        }
    }
}