package com.sample.corexdomain

import kotlinx.coroutines.flow.Flow


/**
 * This abstraction represents an execution unit that will emit an stream, any use case that streams data in the application should implement this contract.
 */
interface FlowUseCase<Request, Response> {
    suspend operator fun invoke(request: Request): Flow<DataSourceResult<Response>>
}
