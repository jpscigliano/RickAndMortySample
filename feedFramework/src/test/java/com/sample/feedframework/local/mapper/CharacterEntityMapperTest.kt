package com.sample.feedframework.local.mapper

import com.sample.feedframework.local.room.mapper.CharacterEntityToModelMapper
import com.sample.feedframework.local.room.mapper.CharacterWithEpisodesEntityToModelMapper
import com.sample.feedframework.mockData.MockDataProvider
import com.sample.feedframework.mockData.MockEntityDataProvider
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import com.sample.feedframework.remote.rest.mapper.CharacterResponseDtoToCharacterMapper
import junit.framework.Assert.assertEquals
import org.junit.Test


internal class CharacterEntityMapperTest {


   // private val characterMapper = CharacterEntityToModelMapper()
    private val characterWithEpisodesMapper = CharacterWithEpisodesEntityToModelMapper()

    /**
     * Scenario: Map CharacterEntityWithEpisodes from Room DD to a Domain Character
     *
     * Given a [CharacterWithEpisodesEntity]
     *
     * When [CharacterWithEpisodesEntityToModelMapper] is executed
     *
     * Then return a [Character]
     *
     */
    @Test
    fun verifyCharacterResponseIsMappedToCharacter() {
        val input = MockEntityDataProvider.characterWithEpisodeEntity

        val expected = MockDataProvider.rick

        val result = characterWithEpisodesMapper.map(input)

        assertEquals(expected, result)
    }

}