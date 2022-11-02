package com.sample.feedframework.local

import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.mapper.Mapper
import com.sample.corexdomain.mapper.toList
import com.sample.corexframework.datasource.LocalDataSource
import com.sample.feeddata.datasource.character.LocalCharactersDataSource
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id
import com.sample.feedframework.local.room.dao.CharactersDao
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterEpisodeCrossRef
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Implementation of a [LocalCharactersDataSource] that uses Room to store and retrieve [CharacterEntity]
 */
internal class RoomCharactersDataSource(
    private val charactersDao: CharactersDao,
    private val characterEntityToModelMapper: Mapper<CharacterEntity, Character>,
    private val characterWithEpisodesEntityToModelMapper: Mapper<CharacterWithEpisodesEntity, Character>,
    private val characterModelToEntityMapper: Mapper<Character, CharacterEntity>,
    private val characterModelToCharacterWithEpisodeEntityMapper: Mapper<List<Character>, List<CharacterEpisodeCrossRef>>,
) : LocalCharactersDataSource {
    override suspend fun observerCharactersList(): Flow<DataSourceResult<List<Character>>> =
        LocalDataSource.getLocalResultAsFlow(
            call = { charactersDao.getAll() },
            entityToModelMapper = { result ->
                characterEntityToModelMapper.toList().map(result)
            }
        )


    override suspend fun getCharacter(id: Id): Flow<DataSourceResult<Character>> =
        LocalDataSource.getLocalResultAsFlow(
            call = { charactersDao.getCharactersWithEpisodes(id.value) },
            entityToModelMapper = { result -> characterWithEpisodesEntityToModelMapper.map(result) }
        )


    override suspend fun saveCharacters(characters: List<Character>) =
        LocalDataSource.insertToLocal(
            transformModelToEntity1 = { characterModelToEntityMapper.toList().map(characters) },
            call1 = { entity -> charactersDao.insert(entity) },
            transformModelToEntity2 = { characterModelToCharacterWithEpisodeEntityMapper.map(characters) },
            call2 = { entity ->
                charactersDao.deleteCharacterWithEpisodes(entity.map { it.characterId })
                charactersDao.insertCharacterWithEpisodes(entity)
            }
        )


    override suspend fun saveCharacter(character: Character) {
        LocalDataSource.insertToLocal(
            transformModelToEntity = { characterModelToEntityMapper.map(character) },
            call = { entity -> charactersDao.insert(entity) }
        )
    }

    override suspend fun deleteCharacters() {
        charactersDao.deleteAll()
    }
}