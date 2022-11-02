package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Character
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterEpisodeCrossRef

/**
 * This Mapper will receive as input [Character] and will return [CharacterEntity] that are use to store and retrieve data from room.
 */
class CharacterModelToCharacterWithEpisodeEntityMapper :
    Mapper<List<Character>, List<CharacterEpisodeCrossRef>> {
    override fun map(input: List<Character>): List<CharacterEpisodeCrossRef> {

        return input.map { character ->
            character.episodesId.map { episodeId ->
                CharacterEpisodeCrossRef(
                    characterId = character.id.value,
                    episodeId = episodeId.value)
            }
        }.flatten()
    }

}