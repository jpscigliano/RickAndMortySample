package com.sample.corexdomain.mapper

/**
 * [Input] Class that will be mapped into an [Output]
 */
interface Mapper<Input, Output> {
    fun map(input: Input): Output
}