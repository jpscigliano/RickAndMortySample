package com.sample.feedframework.local.room.dao


import androidx.room.*
import com.sample.feedframework.local.room.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodeEntity ")
    fun getAll(): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodeEntity WHERE episodeId IN (:id)")
    fun get(id: List<Int>): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(episodes: List<EpisodeEntity>)

    @Delete
    fun delete(episode: EpisodeEntity)

    @Query("DELETE  FROM episodeEntity")
    fun deleteAll()
}