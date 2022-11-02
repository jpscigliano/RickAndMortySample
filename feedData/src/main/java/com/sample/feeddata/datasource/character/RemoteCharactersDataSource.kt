package com.sample.feeddata.datasource.character

import com.sample.corexdomain.DataSourceResult
import com.sample.feeddomain.model.Character
import com.sample.feeddomain.model.Id

interface RemoteCharactersDataSource {
    suspend fun getCharactersList(): DataSourceResult<List<Character>>
    suspend fun getCharacter(id: Id): DataSourceResult<Character>
}