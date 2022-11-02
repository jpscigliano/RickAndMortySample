package com.sample.feeddata.repository

import com.sample.corexdata.fetchAndObserveLocalDatasource
import com.sample.corexdomain.DataSourceResult
import com.sample.feeddata.datasource.character.RemoteCharactersDataSource
import com.sample.feeddata.datasource.character.LocalCharactersDataSource
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of a CharacterRepository that it's in charge of retrieving characters from local or remote.
 */
internal class CharactersRepositoryImpl(
    private val networkCharactersDataSource: RemoteCharactersDataSource,
    private val localCharactersDataSource: LocalCharactersDataSource,
) : CharactersRepository {


    override suspend fun observeCharacters(): Flow<DataSourceResult<List<Character>>> {
        return fetchAndObserveLocalDatasource(
            selectQuery = { localCharactersDataSource.observerCharactersList() },
            networkCall = { networkCharactersDataSource.getCharactersList() },
            insertResultQuery = { localCharactersDataSource.saveCharacters(characters = it) }
        )
    }

    override suspend fun observeCharacter(characterId: Id): Flow<DataSourceResult<Character>> {
        return fetchAndObserveLocalDatasource(
            selectQuery = { localCharactersDataSource.getCharacter(id = characterId) },
            networkCall = { networkCharactersDataSource.getCharacter(id = characterId) },
            insertResultQuery = { localCharactersDataSource.saveCharacter(character = it) }
        )
    }

}