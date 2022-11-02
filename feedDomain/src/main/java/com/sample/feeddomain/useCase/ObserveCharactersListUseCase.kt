package com.sample.feeddomain.useCase

import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.FlowUseCase
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.repository.CharactersRepository

import kotlinx.coroutines.flow.Flow


/**
 * Use Case that emits DataSourceResults wrapping a List of [Character]
 */
 class ObserveCharactersListUseCase(
    private val feedRepository: CharactersRepository,
) : FlowUseCase<Unit, List<@JvmSuppressWildcards Character>> {

    override suspend fun invoke(request: Unit): Flow<DataSourceResult<List<Character>>> =
        feedRepository.observeCharacters()
}