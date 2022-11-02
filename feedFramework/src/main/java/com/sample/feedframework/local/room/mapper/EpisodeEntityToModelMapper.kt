package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.model.Name
import com.sample.feedframework.local.room.entity.EpisodeEntity

/**
 * This Mapper will receive as input [EpisodeEntity] and will return domain model of [Episode]
 */
class EpisodeEntityToModelMapper : Mapper<EpisodeEntity, Episode> {
    override fun map(input: EpisodeEntity): Episode =
        Episode(
            id = Id(input.episodeId),
            name = Name(input.name),
            date = input.date ?: "",
            code = input.code ?: ""
        )
}