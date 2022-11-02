package com.sample.feedframework.mockData

import com.sample.feedframework.remote.rest.dto.CharacterResponseDto
import com.sample.feedframework.remote.rest.dto.EpisodeResponseDto
import com.sample.feedframework.remote.rest.dto.LocationResponseDto

internal object MockResponseDtoDataProvider {

    val locationResponseDto = LocationResponseDto(
        url = "https://rickandmortyapi.com/api/location/1",
        name = "Earth")

    val episodeResponse1 =
        EpisodeResponseDto(id = 1, name = "Pilot", airDate = "December 2, 2013", episode = "S01E01")
    val episodeResponse2 = EpisodeResponseDto(id = 2,
        name = "Lawnmower Dog",
        airDate = "December 9, 2013",
        episode = "S01E02")

    val rickResponse = CharacterResponseDto(
        id = 1,
        name = "Rick Sanchez",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        status = "Alive",
        gender = "Male",
        species = "Human",
        origin = locationResponseDto,
        episodes = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2"),
        location = locationResponseDto,
        type = ""
    )
    val mortyResponse = CharacterResponseDto(
        id = 1,
        name = "Morty Smith",
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        status = "alive",
        gender = "male",
        species = "human",
        origin = locationResponseDto,
        episodes = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2"),
        location = locationResponseDto,
        type = ""
    )


    val characterJson: String =
        "{\"id\":1,\"name\":\"Rick Sanchez\",\"status\":\"Alive\",\"species\":\"Human\",\"type\":\"\",\"gender\":\"Male\",\"origin\":{\"name\":\"Earth\",\"url\":\"https://rickandmortyapi.com/api/location/1\"},\"location\":{\"name\":\"Earth\",\"url\":\"https://rickandmortyapi.com/api/location/1\"},\"image\":\"https://rickandmortyapi.com/api/character/avatar/1.jpeg\",\"episode\":[\"https://rickandmortyapi.com/api/episode/1\",\"https://rickandmortyapi.com/api/episode/2\"],\"url\":\"https://rickandmortyapi.com/api/character/1\",\"created\":\"2017-11-04T18:48:46.250Z\"}"

    val characterJsonArray :String ="{\"info\": { \"count\": 826,\"pages\": 42,\"next\": \"https://rickandmortyapi.com/api/character/?page=2\",\"prev\": null },\"results\": [$characterJson]}"

}