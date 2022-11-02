package com.sample.feeddomain.repository

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun observeCharacters(): Flow<DataSourceResult<List<Character>>>
    suspend fun observeCharacter(characterId: Id): Flow<DataSourceResult<Character>>
}
