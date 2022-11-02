package com.sample.feeddomain.mockData

import com.sample.feeddomain.model.*

internal object MockDataProvider {

    val episode1=Episode(id = Id(value = 1), name = Name(value = "Pilot"), date = "December 2, 2013", code = "S01E01")
    val episode2=Episode(id = Id(value = 2), name = Name(value = "Lawnmower Dog"), date = "December 9, 2013", code = "S01E02")

    val rick= Character(id = Id(value = 1),
        name = Name("Rick Sanchez"),
        imageUrl = ImageUrl("https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
        status =Status.ALIVE,
        gender =Gender.MALE,
        specie =Specie.HUMAN,
        origin = Name("Earth"),
        location = Name("Earth"),
        episodesId = listOf(episode1.id, episode2.id))

    val morty= Character(id = Id(value = 1),
        name = Name("Morty Smith"),
        imageUrl = ImageUrl("https://rickandmortyapi.com/api/character/avatar/2.jpeg"),
        status =Status.ALIVE,
        gender =Gender.MALE,
        specie =Specie.HUMAN,
        origin = Name("Earth"),
        location = Name("Earth"),
        episodesId = listOf(episode1.id, episode2.id))
}