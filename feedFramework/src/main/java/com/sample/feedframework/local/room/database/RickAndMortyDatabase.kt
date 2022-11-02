package com.sample.feedframework.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sample.feedframework.local.room.dao.CharactersDao
import com.sample.feedframework.local.room.dao.EpisodesDao
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterEpisodeCrossRef
import com.sample.feedframework.local.room.entity.EpisodeEntity
import com.sample.feedframework.local.room.mapper.Converters

internal const val DB_NAME = "CHARACTERS_DATABASE"

@Database(entities = [CharacterEntity::class, EpisodeEntity::class,CharacterEpisodeCrossRef::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharactersDao
    abstract fun episodeDao(): EpisodesDao
}

