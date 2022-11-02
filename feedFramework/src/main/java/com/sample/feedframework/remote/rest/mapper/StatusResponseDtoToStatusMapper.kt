package com.sample.feedframework.remote.rest.mapper


import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Status

class StatusResponseDtoToStatusMapper : Mapper<String?, Status> {
    override fun map(input: String?): Status = when (input?.lowercase()) {
        "alive" -> Status.ALIVE
        "dead" -> Status.DEAD
        else -> Status.UNKNOWN
    }
}