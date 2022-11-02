package com.sample.feedframework.remote.rest.mapper


import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id

/**
 *  This mapper  will  try to get and [Id] of a [Episode] from url of type String.
 *
 *  A [Character]fetched from the Api could have a url pointing to the Episode, with the following structure https://rickandmortyapi.com/api/episode/27
 *
 */
class EpisodeUrlToIdMapper : Mapper<String?, Id?> {
    override fun map(input: String?): Id? {
        return runCatching {
            val id: String? = input?.split("/")?.last()
            Id(id?.toInt())
        }.getOrNull()
    }
}