package com.sample.feedframework.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.feeddomain.model.*


@Entity
data class CharacterEntity(
    @PrimaryKey val characterId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "status") val status: Status?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "origin") val origin: String?,
    @ColumnInfo(name = "gender") val gender: Gender?,
    @ColumnInfo(name = "specie") val specie: Specie?,
)