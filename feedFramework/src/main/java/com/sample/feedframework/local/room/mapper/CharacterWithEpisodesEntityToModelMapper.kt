package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.*
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity

class CharacterWithEpisodesEntityToModelMapper : Mapper<CharacterWithEpisodesEntity, Character> {
    override fun map(input: CharacterWithEpisodesEntity): Character {
        return Character(
            id = Id(input.characters.characterId),
            name = Name(input.characters.name),
            imageUrl = ImageUrl(input.characters.image),
            status = input.characters.status ?: Status.UNKNOWN,
            gender = input.characters.gender ?: Gender.UNKNOW,
            specie = input.characters.specie ?: Specie.NOT_SURE,
            origin = Name(input.characters.origin),
            location = Name(input.characters.location),
            episodesId = input.episodes.map { Id(it.episodeId) },
        )
    }
}