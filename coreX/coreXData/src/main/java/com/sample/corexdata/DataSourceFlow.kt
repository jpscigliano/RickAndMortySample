package com.sample.corexdata


import com.sample.corexdomain.DataSourceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * [selectQuery] - Executes a select to be sent as cache data
 * [networkCall] - Makes an async call (e.g. network call), returns the result wrapped in a [DataSourceResultHolder]
 * [insertResultQuery] - Saves the network response as new cache data
 *
 * This function will return a [Flow] emits the following wrappers:
 * [DataSourceResult.Success] - with data from database
 * [DataSourceResult.Error] - if error has occurred with Local data if availables
 * [DataSourceResult.InProgress] - if there is a API network request in progress.
 */
fun <MODEL> fetchAndObserveLocalDatasource(
    selectQuery: suspend () -> Flow<DataSourceResult<MODEL>>,
    networkCall: (suspend () -> DataSourceResult<MODEL>)? = null,
    insertResultQuery: (suspend (MODEL) -> Unit)? = null,
): Flow<DataSourceResult<MODEL>> = flow {

    // if there is no network call, submit data from local and listen for any future changes.
    if (networkCall == null) emitAll(selectQuery())
    else {
        // notify the process started when there is an async call
        emit(DataSourceResult.InProgress())
        // Make network call
        when (val responseStatus = networkCall()) {
            is DataSourceResult.Success -> {
                // save data to to local if successful
                insertResultQuery?.invoke(responseStatus.data!!)
                // submit new local and listen for any future changes
                emitAll(selectQuery())
            }
            else -> {
                //Emit AppError with local data
                emit(responseStatus.updateData(selectQuery().firstOrNull()?.dataOrNull()))
            }
        }
    }
}


/**
 *
 * Function notify UI about:
 * [DataSourceResult.Success] - success with response
 * [DataSourceResult.Error] - if error has occurred
 * [DataSourceResult.InProgress]  - if there is a API network request in progress.
 *
 * @param asyncCall Makes an async call (e.g. network call), returns the result wrapped in a [DataSourceResult]
 * @param insertResultQuery Saves the network response as new cache data
 */
fun <MODEL> fetchRemoteAndStore(
    asyncCall: (suspend () -> DataSourceResult<MODEL>),
    insertResultQuery: (suspend (MODEL) -> Unit)? = null,
): Flow<DataSourceResult<MODEL>> {
    return flow {

        // notify the process started
        emit(DataSourceResult.InProgress())

        // call network
        val responseStatus = asyncCall()

        // save to db
        if (responseStatus is DataSourceResult.Success) {
            insertResultQuery?.invoke(responseStatus.data!!)
        }

        // emit response
        emit(responseStatus)
    }
}


/**
 * [selectQuery] - Executes a select to be sent as cache data
 * [networkCall] - Makes an async call (e.g. network call), returns the result wrapped in a [DataSourceResultHolder]
 * [insertResultQuery] - Saves the network response as new cache data
 *
 * This function will return a [Flow] emits the following wrappers:
 * [DataSourceResult.Success] - with data from database
 * [DataSourceResult.Error] - if error has occurred with Local data if availables
 * [DataSourceResult.InProgress] - if there is a API network request in progress.
 */
suspend fun <MODEL> fetchAndReturnFromLocalDatasource(
    selectQuery: suspend () -> DataSourceResult<MODEL>,
    networkCall: (suspend () -> DataSourceResult<MODEL>)? = null,
    insertResultQuery: (suspend (MODEL) -> Unit)? = null,
): DataSourceResult<MODEL> {

    // if there is no network call, submit data from local.
    return if (networkCall == null) selectQuery()
    else {
        // Make network call
        when (val responseStatus = networkCall()) {
            is DataSourceResult.Success -> {
                // save data to to local if successful
                insertResultQuery?.invoke(responseStatus.data!!)
                // submit new local and listen for any future changes
                selectQuery()
            }
            else -> {
                //Emit AppError with local data
                responseStatus.updateData(selectQuery().dataOrNull())
            }
        }
    }
}


/**
 *
 * Function returns one status, without notifying about progress
 *
 * @param asyncCall Makes an async call (e.g. network call), returns the result wrapped in a [DataSourceResult]
 */
suspend fun <MODEL> fetchRemoteOnce(
    asyncCall: (suspend () -> DataSourceResult<MODEL>),
    insertResultQuery: (suspend (MODEL) -> Unit)? = null,
): DataSourceResult<MODEL> {
    return withContext(Dispatchers.IO) {
        // call network
        val responseStatus = asyncCall()

        // save data if successful
        if (responseStatus is DataSourceResult.Success) {
            insertResultQuery?.invoke(responseStatus.data!!)
        }

        responseStatus
    }
}

/**
 * Save data, that's it
 */
@JvmName("insertResultOnce")
suspend fun insertToRemoteOnce(
    insertResultQuery: suspend () -> Unit,
): DataSourceResult<Boolean> {
    return withContext(Dispatchers.IO) {
        insertResultQuery()
        DataSourceResult.Success(true)
    }
}

/**
 * [selectQuery] - Executes a select to be sent as cache data
 */
suspend fun <MODEL> fetchRemoteOnce(
    selectQuery: suspend () -> DataSourceResult<MODEL>,
): DataSourceResult<MODEL> {
    return withContext(Dispatchers.IO) {
        selectQuery()
    }
}