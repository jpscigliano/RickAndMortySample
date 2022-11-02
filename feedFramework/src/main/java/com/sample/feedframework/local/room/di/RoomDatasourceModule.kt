package com.sample.feedframework.local.room.di

import androidx.room.Room
import com.sample.feeddata.datasource.character.LocalCharactersDataSource
import com.sample.feeddata.datasource.episode.LocalEpisodeDataSource
import com.sample.feedframework.local.RoomCharactersDataSource
import com.sample.feedframework.local.RoomEpisodesDataSource
import com.sample.feedframework.local.room.database.DB_NAME
import com.sample.feedframework.local.room.database.RickAndMortyDatabase
import com.sample.feedframework.local.room.mapper.*
import org.koin.dsl.module

val roomDatasourceModule = module {

    single {
        Room.databaseBuilder(
            get(),
            RickAndMortyDatabase::class.java,
            DB_NAME
        ).build()
    }

    single { get<RickAndMortyDatabase>().characterDao() }
    single { get<RickAndMortyDatabase>().episodeDao() }

    single<LocalCharactersDataSource> {
        RoomCharactersDataSource(
            charactersDao = get(),
            characterEntityToModelMapper = CharacterEntityToModelMapper(),
            characterModelToEntityMapper = CharacterModelToEntityMapper(),
            characterModelToCharacterWithEpisodeEntityMapper = CharacterModelToCharacterWithEpisodeEntityMapper(),
            characterWithEpisodesEntityToModelMapper = CharacterWithEpisodesEntityToModelMapper(),

            )
    }

    single<LocalEpisodeDataSource> {
        RoomEpisodesDataSource(
            episodesDao = get(),
            episodeEntityToModelMapper = EpisodeEntityToModelMapper(),
            episodeModelToEntityMapper = EpisodeModelToEntityMapper()
        )
    }

}