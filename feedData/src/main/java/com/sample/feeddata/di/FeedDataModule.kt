package com.sample.feeddata.di

import com.sample.feeddata.repository.CharactersRepositoryImpl
import com.sample.feeddata.repository.EpisodesRepositoryImpl
import com.sample.feeddomain.repository.CharactersRepository
import com.sample.feeddomain.repository.EpisodeRepository
import org.koin.dsl.module

val feedDataModule = module {
    single<CharactersRepository> {
        CharactersRepositoryImpl(
            networkCharactersDataSource = get(),
            localCharactersDataSource = get()
        )
    }
    single<EpisodeRepository> {
        EpisodesRepositoryImpl(
            networkEpisodesDataSource = get(),
            localEpisodesDataSource = get()
        )
    }
}