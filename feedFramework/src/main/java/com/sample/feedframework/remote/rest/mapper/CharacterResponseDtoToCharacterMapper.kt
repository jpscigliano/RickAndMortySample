package com.sample.feedframework.remote.rest.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.corexdomain.mapper.toList
import com.sample.feeddomain.model.*
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto


class CharacterResponseDtoToCharacterMapper(
    private val statusMapper: Mapper<String?, Status>,
    private val genderMapper: Mapper<String?, Gender>,
    private val specieMapper: Mapper<String?, Specie>,
    private val urlMapper: Mapper<String?, Id?>,
) : Mapper<CharacterResponseDto?, Character> {

    override fun map(input: CharacterResponseDto?): Character = Character(
        id = Id(input?.id ?: -1),
        name = Name(input?.name),
        imageUrl = ImageUrl(input?.image),
        status = statusMapper.map(input?.status),
        gender = genderMapper.map(input?.gender),
        specie = specieMapper.map(input?.species),
        episodesId = urlMapper.toList().map(input?.episodes ?: listOf()).filterNotNull(),
        origin = Name(input?.origin?.name),
        location = Name(input?.location?.name),
    )

}
