package com.sample.feeddomain.useCase

import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult
import com.sample.corexdomain.DataSourceResult.*
import com.sample.corexdomain.FlowUseCase
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.CharacterDetail
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.repository.CharactersRepository
import com.sample.feeddomain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use Case that emits DataSourceResults wrapping a [CharacterDetail].
 *
 * First observe the [Character] then fetch the list of [Episode].
 * If one of the two calls returns and [Error] then map the data result in a [DataSourceResult.Error]
 */
class ObserveCharacterDetailUseCase(
    private val feedRepository: CharactersRepository,
    private val episodesRepository: EpisodeRepository,
) : FlowUseCase<Id, CharacterDetail> {

    override suspend fun invoke(request: Id): Flow<DataSourceResult<CharacterDetail>> {

        return feedRepository.observeCharacter(request).map { characterResult ->

            val character: Character? = characterResult.dataOrNull()

            when (characterResult) {
                is InProgress -> InProgress()
                is Error, is Success -> {
                    if (character == null) return@map Error(AppError.CharacterUnavailable)

                    when (val episodeResult =
                        episodesRepository.getEpisodes(character.episodesId)) {
                        is InProgress -> InProgress()
                        is Error, is Success -> episodeResult.mapWithData(
                            CharacterDetail(
                                character = character,
                                episodes = episodeResult.dataOrNull() ?: listOf()))
                    }
                }
            }
        }
    }
}
