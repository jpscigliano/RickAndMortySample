package com.sample.feedframework.remote.rest.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.model.Name
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto

class EpisodeResponseDtoToEpisodeMapper : Mapper<EpisodeResponseDto, Episode> {
    override fun map(input: EpisodeResponseDto): Episode {
        return Episode(
            id = Id(value = input.id),
            name = Name(value = input.name),
            date = input.airDate,
            code = input.episode)
    }
}