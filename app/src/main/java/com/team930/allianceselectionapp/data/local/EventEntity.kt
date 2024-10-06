package com.team930.allianceselectionapp.data.local

import androidx.room.Dao
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

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey val competitionId: Int,
    val year: Int,
    val activeSeason: Boolean,
    val name: String,
    val eventKey: String? = null
)

data class EventWithTeams(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "competitionId",
        entityColumn = "teamNumber",
        associateBy = Junction(TeamEventCrossRef::class)
    )
    val teams: List<TeamEntity>
)

@Dao
abstract class EventDao {
    @Transaction
    @Query("SELECT * FROM event")
    abstract fun getAll(): Flow<List<EventEntity>>

    @Transaction
    @Query("SELECT * FROM event WHERE competitionId = :competitionId")
    abstract fun getEventById(competitionId: Int): Flow<EventEntity?>

    @Transaction
    @Query("SELECT * FROM event WHERE competitionId = :competitionId")
    abstract fun getEventWithTeams(competitionId: Int): Flow<EventWithTeams>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(events: List<EventEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTeamEventCrossRef(crossRef: TeamEventCrossRef)
}