package com.team930.allianceselectionapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Entity(tableName = "team")
data class TeamEntity(
    @PrimaryKey val teamNumber: String,
)

data class TeamWithEvents(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamNumber",
        entityColumn = "competitionId",
        associateBy = Junction(TeamEventCrossRef::class)
    )
    val events: List<EventEntity>
)

@Dao
abstract class TeamDao {
    @Transaction
    @Query("SELECT * FROM team")
    abstract fun getAll(): Flow<List<TeamEntity>>

    @Transaction
    @Query("Select * FROM team WHERE teamNumber = :teamNumber")
    abstract fun getTeamByNumber(teamNumber: String): Flow<TeamEntity>

    @Transaction
    @Query("SELECT * FROM team WHERE teamNumber = :teamNumber")
    abstract fun getTeamWithEvents(teamNumber: String): Flow<TeamWithEvents>

    fun followTeamByNumberLive(teamNumber: String) =
        getTeamByNumber(teamNumber).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(teams: List<TeamEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTeamEventCrossRef(crossRef: TeamEventCrossRef)

    @Delete
    abstract fun delete(team: TeamEntity)
}