package com.sample.corexframework.datasource

import com.sample.corexdomain.DataSourceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


object LocalDataSource {
    /**
     *  Helper class that will execute a call to a local source and maps the result to domain.
     *  If mappers are provided, it will map the requests and response.
     *
     * @param call the remote call - returning a local model.
     * @param entityToModelMapper transforms from the local  model to the domain model
     */
    suspend fun <MODEL_LOCAL, MODEL> getLocalResultAsFlow(
        coroutineContext: CoroutineContext = Dispatchers.Default,
        call: () -> Flow<MODEL_LOCAL>,
        entityToModelMapper: (MODEL_LOCAL) -> MODEL,
    ): Flow<DataSourceResult<MODEL>> = withContext(coroutineContext){
         call().map { it?.let { entityToModelMapper(it) } }
            .map { DataSourceResult.Success(it) }
            .flowOn(coroutineContext)
    }

    suspend fun <MODEL_LOCAL, MODEL> getLocalResult(
        coroutineContext: CoroutineContext = Dispatchers.Default,
        call: () -> MODEL_LOCAL,
        entityToModelMapper: (MODEL_LOCAL) -> MODEL,
    ): DataSourceResult<MODEL> = withContext(coroutineContext) {
        DataSourceResult.Success(entityToModelMapper(call()))
    }

    suspend fun <MODEL_ROOM> insertToLocal(
        coroutineContext: CoroutineContext=Dispatchers.Default,
        transformModelToEntity: () -> MODEL_ROOM,
        call: suspend (MODEL_ROOM) -> Unit,
    ) {
        withContext(coroutineContext) {
            call(transformModelToEntity())
        }
    }

    suspend fun <MODEL_ROOM1,MODEL_ROOM2> insertToLocal(
        coroutineContext: CoroutineContext=Dispatchers.Default,
        transformModelToEntity1: () -> MODEL_ROOM1,
        call1: suspend (MODEL_ROOM1) -> Unit,
        transformModelToEntity2: () -> MODEL_ROOM2,
        call2: suspend (MODEL_ROOM2) -> Unit,
    ) {
        withContext(coroutineContext) {
            call1(transformModelToEntity1())
            call2(transformModelToEntity2())
        }
    }

}