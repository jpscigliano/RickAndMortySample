package com.sample.feeddomain.useCase

import com.sample.corexdomain.AppError
import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.mockData.MockDataProvider
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.CharacterDetail
import com.sample.feeddomain.model.Episode
import com.sample.feeddomain.model.Id
import com.sample.feeddomain.repository.CharactersRepository
import com.sample.feeddomain.repository.EpisodeRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ObserveCharacterDetailUseCaseTest {


    private val charactersRepository = mockk<CharactersRepository>()
    private val episodeRepository = mockk<EpisodeRepository>()

    private val observeCharactersListUseCase by lazy {
        ObserveCharacterDetailUseCase(feedRepository = charactersRepository,
            episodesRepository = episodeRepository)
    }

    private val mockedCharacter: Character = MockDataProvider.rick
    private val mockedEpisodes: List<Episode> = listOf(MockDataProvider.episode1, MockDataProvider.episode2)


    @Before
    fun setup() {
        coEvery { charactersRepository.observeCharacter(mockedCharacter.id) } returns flowOf(
            DataSourceResult.InProgress(),
            DataSourceResult.Success(mockedCharacter))
    }


    /**
     * Scenario: User observes for a  [CharacterDetail]
     *
     * Given the User provides an [Id] AND the CharacterRepository emits a Success with character AND the EpisodeRepository emit a Success with episodes.
     *
     * When [ObserveCharacterDetailUseCase] is executed
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Success] with a  [CharacterDetail] is collected.
     *
     */
    @Test
    fun getSuccessResultWithCharacterDetailData()  = runTest {
        coEvery {
            episodeRepository.getEpisodes(mockedCharacter.episodesId)
        } returns DataSourceResult.Success(mockedEpisodes)

        val input = mockedCharacter.id
        val result = observeCharactersListUseCase(input).toList()

        assertEquals(DataSourceResult.InProgress<CharacterDetail>()::class, result.first()::class)
        assertEquals(mockedCharacter, result[1].dataOrNull()?.character)
        assertEquals(mockedEpisodes, result[1].dataOrNull()?.episodes)
    }

    /**
     * Scenario: User observes for a [CharacterDetail]
     *
     * Given the User provides an [Id] AND the CharacterRepository emits a Success with character AND the EpisodeRepository emit a UnknownError api error with a list episodes.
     *
     * When [ObserveCharacterDetailUseCase] is executed
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Error] with a [CharacterDetail] is collected.
     *
     */
    @Test
    fun getErrorResultWithCharacterDetailData() = runTest {
        coEvery {
            episodeRepository.getEpisodes(mockedCharacter.episodesId)
        } returns DataSourceResult.Error(AppError.ApiError.UnknownError, mockedEpisodes)

        val input = mockedCharacter.id
        val result = observeCharactersListUseCase(input).toList()

        assertEquals(DataSourceResult.InProgress<CharacterDetail>()::class, result.first()::class)
        assertEquals(mockedCharacter, result[1].dataOrNull()?.character)
        assertEquals(mockedEpisodes, result[1].dataOrNull()?.episodes)
    }


    /**
     * Scenario: User observes for a [CharacterDetail] with no Internet.
     *
     * Given the User provides an [Id] AND the CharacterRepository emits a [AppError.NoInternetAvailable] with characters AND the EpisodeRepository emits a [AppError.NoInternetAvailable] with a list episodes.
     *
     * When [ObserveCharacterDetailUseCase] is executed
     *
     * Then a [DataSourceResult.InProgress] and [DataSourceResult.Error] with a [CharacterDetail] is collected.
     *
     */
    @Test
    fun getInternetErrorResultWithCharacterDetailData()  = runTest {

        coEvery { episodeRepository.getEpisodes(mockedCharacter.episodesId) } returns
                DataSourceResult.Error(AppError.NoInternetAvailable, mockedEpisodes)

        val input = mockedCharacter.id
        val result = observeCharactersListUseCase(input).toList()

        assertEquals(DataSourceResult.InProgress<CharacterDetail>()::class, result.first()::class)
        assertEquals(mockedCharacter, result[1].dataOrNull()?.character)
        assertEquals(mockedEpisodes, result[1].dataOrNull()?.episodes)
    }

}