package com.sample.corexframework.datasource

interface ApiResponse<T> {
    val isSuccessful: Boolean
    fun code(): Int
    suspend fun body(): T?
}