package com.parentAps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.showloading<T>())
        val source = databaseQuery.invoke().map { Result.success(it) }
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            emit(Result.hideloading<T>())
            saveCallResult(responseStatus.data!!)
            emitSource(source)
        } else if (responseStatus.status == Result.Status.ERROR) {
            emit(Result.hideloading<T>())
            emit(Result.error<T>(responseStatus.message!!))
            emitSource(source)
        }
    }