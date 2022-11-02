package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Character
import com.sample.feedframework.local.room.entity.CharacterEntity

/**
 * This Mapper will receive as input [Character] and will return [CharacterEntity] that are use to store and retrieve data from room.
 */
class CharacterModelToEntityMapper : Mapper<Character, CharacterEntity> {
    override fun map(input: Character): CharacterEntity =
        CharacterEntity(
            characterId = input.id.value,
            name = input.name?.value,
            image = input.imageUrl?.value,
            status = input.status,
            gender = input.gender,
            specie = input.specie,
            origin = input.origin?.value,
            location = input.origin?.value
        )
}