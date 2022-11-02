package com.sample.feedframework.remote.rest.mapper


import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Specie

class SpecieResponseDtoToSpecieMapper : Mapper<String?, Specie> {
    override fun map(input: String?): Specie = when (input?.lowercase()) {
        "human" -> Specie.HUMAN
        "humanoide" -> Specie.HUMANOID
        else -> Specie.NOT_SURE
    }
}