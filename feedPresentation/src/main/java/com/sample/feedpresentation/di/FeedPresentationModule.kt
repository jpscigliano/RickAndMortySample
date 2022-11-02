package com.sample.feedpresentation.di

import com.sample.feedpresentation.characterDetail.CharacterDetailViewModel
import com.sample.feedpresentation.characterList.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val feedPresentationModule = module {
    viewModel { CharactersViewModel(getCharacterListUseCase = get()) }
    viewModelOf(:: CharacterDetailViewModel)
}