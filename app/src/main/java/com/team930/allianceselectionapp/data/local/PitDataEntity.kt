package com.team930.allianceselectionapp.data.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "PitData")
data class PitDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val teamNumber: String,
    val competitionId: Int,
    val driveTrain: String? = null,
    val robotWidth: Double? = null,
    val programmingLanguage: String? = null
)

data class PitDataWithTeamAndEvent(
    @Embedded val pitData: PitDataEntity,
    @Relation(
        parentColumn = "teamNumber",
        entityColumn = "teamNumber"
    )
    val team: TeamEntity,
    @Relation(
        parentColumn = "competitionId",
        entityColumn = "competitionId"
    )
    val event: EventEntity
)

@Dao
abstract class PitDataDao {
    @Transaction
    @Query("SELECT * FROM PitData WHERE id = :id")
    abstract fun getPitDataWithTeamAndEvent(id: Long): Flow<PitDataWithTeamAndEvent>

    @Query("SELECT * FROM PitData WHERE competitionId = :competitionId")
    abstract fun getPitDataByEvent(competitionId: Int): Flow<List<PitDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPitData(pitData: PitDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(pitData: List<PitDataEntity>): List<Long>

    @Update
    abstract suspend fun updatePitData(pitData: PitDataEntity)

    @Query("SELECT * FROM PitData WHERE competitionId = :competitionId AND teamNumber = :teamNumber LIMIT 1")
    abstract suspend fun getPitDataByCompetitionAndTeam(competitionId: Int, teamNumber: Int): PitDataEntity?

}