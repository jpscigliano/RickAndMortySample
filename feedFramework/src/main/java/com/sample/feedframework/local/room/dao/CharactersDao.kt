package com.sample.feedframework.local.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.feedframework.local.room.entity.CharacterEntity
import com.sample.feedframework.local.room.entity.CharacterEpisodeCrossRef
import com.sample.feedframework.local.room.entity.CharacterWithEpisodesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characterEntity")
    fun getAll(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: CharacterEntity)

    @Query("DELETE  FROM characterEntity")
    fun deleteAll()

    @Query("SELECT * FROM characterEntity WHERE characterId IN (:characterId)")
    fun getCharactersWithEpisodes(characterId: Int): Flow<CharacterWithEpisodesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterWithEpisodes(characterWithEpisodes: List<CharacterEpisodeCrossRef>)

    @Query("DELETE  FROM characterEpisodeCrossRef WHERE  characterId IN (:characterIds)")
    fun deleteCharacterWithEpisodes(characterIds: List<Int>)


}