package com.sample.feedframework.local.room.mapper

import androidx.room.TypeConverter
import com.sample.feeddomain.model.*

/**
 * A useful group of converters that will transform [Enum] into primitive data types that would be  store later in our Databases.
 */
class Converters {


    @TypeConverter
    fun toGender(value: Int) = enumValues<Gender>()[value]

    @TypeConverter
    fun fromGender(value: Gender) = value.ordinal

    @TypeConverter
    fun toStatus(value: Int) = enumValues<Status>()[value]

    @TypeConverter
    fun fromStatus(value: Status) = value.ordinal

    @TypeConverter
    fun toSpecie(value: Int) = enumValues<Specie>()[value]

    @TypeConverter
    fun fromSpecie(value: Specie) = value.ordinal


}