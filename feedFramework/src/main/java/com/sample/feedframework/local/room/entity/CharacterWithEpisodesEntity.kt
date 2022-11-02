package com.sample.feedframework.local.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation


data class CharacterWithEpisodesEntity(
    @Embedded val characters: CharacterEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "characterId",
    )
    val episodes: List<CharacterEpisodeCrossRef>,
)

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisodeCrossRef(
    val characterId: Int,
    val episodeId: Int,
)