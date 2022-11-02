package com.sample.feedframework.mockData

import com.sample.feeddomain.model.Gender
import com.sample.feeddomain.model.Specie
import com.sample.feeddomain.model.Status
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterEpisodeCrossRef
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import com.sample.feedframework.local.room.entity.EpisodeEntity

internal object MockEntityDataProvider {

    val rickEntity = CharacterEntity(
        characterId = 1,
        name = "Rick Sanchez",
        status = Status.ALIVE,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        location = "Earth",
        origin = "Earth",
        gender = Gender.MALE,
        specie = Specie.HUMAN)

    val mortyEntity = CharacterEntity(
        characterId = 2,
        name = "Morty Smith",
        status = Status.ALIVE,
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        location = "Earth",
        origin = "Earth",
        gender = Gender.MALE,
        specie = Specie.HUMAN)


    val episode1Entity = EpisodeEntity(
        episodeId = 1,
        name = "Pilot",
        date = "December 2, 2013",
        code = "S01E01")

    val episode2Entity = EpisodeEntity(
        episodeId = 2,
        name = "Lawnmower Dog",
        date = "December 9, 2013",
        code = "S01E02")

    val characterEpisodeCrossRef = listOf(CharacterEpisodeCrossRef(
        characterId = 1,
        episodeId = 1
    ), CharacterEpisodeCrossRef(
        characterId = 1,
        episodeId = 2
    ))

    val characterWithEpisodeEntity = CharacterWithEpisodesEntity(
        characters = rickEntity,
        episodes = characterEpisodeCrossRef)

}