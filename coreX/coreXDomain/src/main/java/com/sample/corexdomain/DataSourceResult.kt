package com.sample.corexdomain

/**
 * This abstraction is a wrapper over a result of certain types of  executions, like a result of API or a call to a DB.
 * [InProgress]
 * [NoInternet] There was not connectivity during the execution.
 */
sealed interface DataSourceResult<DATA> {
    class InProgress<DATA> : DataSourceResult<DATA>
   // class NoInternet<DATA> : DataSourceResult<DATA>
    data class Error<DATA>(val error: AppError, val data: DATA? = null) : DataSourceResult<DATA>
    data class Success<DATA>(val data: DATA?) : DataSourceResult<DATA>

    fun dataOrNull(): DATA? {
        return when (this) {
            is Error -> this.data
            is Success -> this.data
            else -> null
        }
    }

    fun updateData(data: DATA?): DataSourceResult<DATA> {
        return when (this) {
            is InProgress-> this
            is Error -> this.copy(data = data)
            is Success -> this.copy(data = data)

        }
    }
    fun <NEW_DATA>mapWithData(newData: NEW_DATA): DataSourceResult<NEW_DATA>{
        return when(this){
            is Error -> Error(this.error,newData)
            is InProgress -> InProgress()
            is Success -> Success(newData)
        }
    }
}


