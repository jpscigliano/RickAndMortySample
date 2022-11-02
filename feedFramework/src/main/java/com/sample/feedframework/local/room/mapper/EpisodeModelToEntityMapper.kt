package com.sample.feedframework.local.room.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Episode
import com.sample.feedframework.local.room.entity.EpisodeEntity

/**
 * This Mapper will receive as input [Episode] and will return [EpisodeEntity] that are use to store and retrieve data from room.
 */
class EpisodeModelToEntityMapper : Mapper<Episode, EpisodeEntity> {
    override fun map(input: Episode): EpisodeEntity =
        EpisodeEntity(
            episodeId = input.id.value,
            name = input.name.value,
            code = input.code,
            date = input.date
        )
}