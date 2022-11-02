package com.sample.feeddata.datasource.character

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import kotlinx.coroutines.flow.Flow


interface LocalCharactersDataSource {

    suspend fun observerCharactersList(): Flow<DataSourceResult<List<Character>>>
    suspend fun getCharacter(id: Id): Flow<DataSourceResult<Character>>

    suspend fun saveCharacters(characters: List<Character>)
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacters()
}