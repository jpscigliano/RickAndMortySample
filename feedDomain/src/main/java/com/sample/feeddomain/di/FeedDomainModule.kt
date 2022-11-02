package com.sample.feeddomain.di

import com.sample.feeddomain.useCase.ObserveCharacterDetailUseCase
import com.sample.feeddomain.useCase.ObserveCharactersListUseCase
import org.koin.dsl.module

val feedDomainModule = module {
    single { ObserveCharactersListUseCase(feedRepository = get()) }
    single { ObserveCharacterDetailUseCase(feedRepository = get(), episodesRepository = get()) }
}
