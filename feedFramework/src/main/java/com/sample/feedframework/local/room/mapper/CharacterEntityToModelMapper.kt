package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.*
import com.sample.feedframework.local.room.entity.CharacterEntity

/**
 * This Mapper will receive as input [CharacterEntity] and will return domain model of [Character]
 */
class CharacterEntityToModelMapper : Mapper<CharacterEntity, Character> {
    override fun map(input: CharacterEntity): Character =
        Character(
            id = Id(input.characterId),
            name = Name(input.name),
            imageUrl = ImageUrl(input.image),
            status = input.status ?: Status.UNKNOWN,
            gender = input.gender ?: Gender.UNKNOW,
            specie = input.specie ?: Specie.NOT_SURE,
            origin = Name(input.origin),
            location = Name(input.location),
            episodesId = listOf(),
        )
}