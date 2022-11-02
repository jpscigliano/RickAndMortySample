package com.sample.feeddomain.model

data class Character(
    val id: Id,
    val name: Name?,
    val imageUrl: ImageUrl?,
    val status: Status,
    val gender: Gender,
    val specie: Specie,
    val origin: Name?,
    val location: Name?,
    val episodesId: List<Id>,
)