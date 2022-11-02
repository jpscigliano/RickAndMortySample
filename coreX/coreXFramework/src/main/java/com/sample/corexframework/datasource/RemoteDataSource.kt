package com.sample.corexframework.datasource


import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult
import com.sample.corexframework.errorParser.ErrorParser
import com.sample.corexframework.utils.NetworkingUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object RemoteDataSource {

    /**
     *  Helper class that will verify if there is internet connection and executes the ApiCall.
     *  If mappers are provided, it will map the requests and response.
     *
     * @param mapModelToRequestDto prepares the model to be sent in the request - if nothing is supposed to be sent then write 'mapModelToRequestDto = {}'
     * @param call the api call - returning a [ApiResponse]
     * @param mapResponseDtoToModel transforms from the api response model to the domain module
     */
    suspend fun <REQUEST_API_MODEL, RESPONSE_API_MODEL, MODEL> getRemoteResult(
        mapModelToRequestDto: (suspend () -> REQUEST_API_MODEL)? = null,
        call: suspend (REQUEST_API_MODEL?) -> ApiResponse<RESPONSE_API_MODEL>,
        mapResponseDtoToModel: (suspend (RESPONSE_API_MODEL) -> MODEL),
        errorParser: ErrorParser,
    ): DataSourceResult<MODEL> {
        try {
            // verify internet
            if (withContext(Dispatchers.IO) { !NetworkingUtils.hasInternetConnection() }) {
                return DataSourceResult.Error(AppError.NoInternetAvailable)
            }

            // call api
            val response = call(mapModelToRequestDto?.invoke())

            return when {
                //////////////////////////
                // SUCCESSFUL
                response.isSuccessful && response.body() != null -> {
                    // map/transform
                    mapResponseDtoToModel(response.body()!!).let { model ->
                        DataSourceResult.Success(model)
                    }
                }

                //////////////////////////
                // UNSUCCESSFUL
                !response.isSuccessful -> {
                    // parse and return error
                    errorParser.parseError(response.code()).let { appError ->
                        DataSourceResult.Error(appError)
                    }
                }

                //////////////////////////
                // ELSE
                else -> {
                    DataSourceResult.Error(AppError.ApiError.UnknownError)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataSourceResult.Error(AppError.ApiError.UnknownError)
        }
    }
}
