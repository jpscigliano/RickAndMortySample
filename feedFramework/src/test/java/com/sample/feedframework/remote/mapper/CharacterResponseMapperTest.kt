package com.sample.feedframework.remote.mapper

import com.sample.corexdomain.mapper.toList
import com.sample.feeddomain.model.Id
import com.sample.feedframework.mockData.MockDataProvider
import com.sample.feedframework.mockData.MockResponseDtoDataProvider
import com.sample.feedframework.remote.rest.mapper.*
import junit.framework.Assert.assertEquals
import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import org.junit.Test


internal class CharacterResponseMapperTest {

    private val statusMapper = StatusResponseDtoToStatusMapper()
    private val genderMapper = GenderResponseDtoToGenderMapper()
    private val specieMapper = SpecieResponseDtoToSpecieMapper()
    private val urlMapper = EpisodeUrlToIdMapper()
    private val characterMapper =
        CharacterResponseDtoToCharacterMapper(statusMapper, genderMapper, specieMapper, urlMapper)

    /**
     * Scenario: Map EpisodeUrl from API to a list of Episode Id.
     *
     * Given a Url as a String
     *
     * When [EpisodeUrlToIdMapper] is executed
     *
     * Then  return a  list of episodes [Id]
     *
     */
    @Test
    fun verifyEpisodeUrlResponseIsMappedToId() {
        val input = MockResponseDtoDataProvider.rickResponse.episodes!!

        val expected = MockDataProvider.rick.episodesId

        val result = urlMapper.toList().map(input)

        assertEquals(expected, result)
    }

    /**
     * Scenario: Map CharacterResponse from API to a Domain Character
     *
     * Given a [CharacterResponseDto]
     *
     * When [CharacterResponseDtoToCharacterMapper] is executed
     *
     * Then  return a [Character]
     *
     */
    @Test
    fun verifyCharacterResponseIsMappedToCharacter() {
        val input = MockResponseDtoDataProvider.rickResponse

        val expected = MockDataProvider.rick

        val result = characterMapper.map(input)

        assertEquals(expected, result)
    }

}