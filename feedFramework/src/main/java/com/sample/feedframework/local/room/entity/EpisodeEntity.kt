package com.sample.feedframework.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.feeddomain.model.Status


@Entity
data class EpisodeEntity(
    @PrimaryKey val episodeId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "code") val code: String?,
)