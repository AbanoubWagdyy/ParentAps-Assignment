package com.parentAps.data

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        SHOW_LOADING,
        HIDE_LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> showloading(data: T? = null): Result<T> {
            return Result(Status.SHOW_LOADING, data, null)
        }

        fun <T> hideloading(data: T? = null): Result<T> {
            return Result(Status.HIDE_LOADING, data, null)
        }
    }
}