package com.sample.corexframework.errorParser

import com.sample.corexdomain.AppError

interface ErrorParser {
    fun parseError(responseErrorCode: Int): AppError
}
