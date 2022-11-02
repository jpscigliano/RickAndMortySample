package com.sample.corexdomain

/**
 * Class for representing different types of errors.
 */
sealed class AppError(val message: String) {
    sealed class ApiError(message: String) : AppError(message) {
        object InvalidRequest : ApiError("Invalid")
        object NotFound : ApiError("Not Found")
        object UnknownError : ApiError("Unknown Error")
    }
    object NoInternetAvailable : AppError("No internet available")
    object CharacterUnavailable : AppError("Unable to fetch data of the character")
}