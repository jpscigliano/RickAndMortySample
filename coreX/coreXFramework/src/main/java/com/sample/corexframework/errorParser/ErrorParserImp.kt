package com.sample.corexframework.errorParser

import com.sample.corexdomain.AppError

class ErrorParserImp : ErrorParser {
    override fun parseError(responseErrorCode: Int): AppError {
        return when (responseErrorCode) {
            // invalid request
            400 -> AppError.ApiError.InvalidRequest

            // not found
            404 -> AppError.ApiError.NotFound

            // etc..

            else -> AppError.ApiError.UnknownError
        }
    }
}
