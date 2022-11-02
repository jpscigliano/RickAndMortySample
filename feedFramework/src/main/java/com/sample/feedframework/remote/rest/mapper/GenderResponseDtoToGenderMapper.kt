package com.sample.feedframework.remote.rest.mapper

import com.sample.corexdomain.mapper.Mapper
import com.sample.feeddomain.model.Gender


class GenderResponseDtoToGenderMapper : Mapper<String?, Gender> {
    override fun map(input: String?): Gender = when (input?.lowercase()) {
        "male" -> Gender.MALE
        "female" -> Gender.FEMALE
        "genderless" -> Gender.GENDERLESS
        else -> Gender.UNKNOW
    }
}