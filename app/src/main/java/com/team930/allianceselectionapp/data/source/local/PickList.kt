package com.team930.allianceselectionapp.data.source.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.team930.allianceselectionapp.data.local.TeamEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Entity(tableName = "picklist")
data class PickList(
    @PrimaryKey val pickListId: Long,
    val pickListName: String
)

@Entity(primaryKeys = ["pickListId", "teamNumber"])
data class PickListTeamCrossRef(
    val pickListId: Long,
    val teamNumber: Long
)

data class PickListWithTeams(
    @Embedded val pickList: PickList,
    @Relation(
        parentColumn = "pickListId",
        entity = TeamEntity::class,
        entityColumn = "teamNumber",
        associateBy = Junction(PickListTeamCrossRef::class)
    )
    val teams: List<TeamEntity>
)

@Dao
abstract class PickListDao {
    @Query("SELECT * FROM picklist")
    abstract fun getAll(): Flow<List<PickList>>

//    @Insert
//    abstract fun insert(pickListWithTeams: PickListWithTeams): Long

    @Insert
    abstract fun insert(pickList: PickList): Long

//    @Update
//    abstract fun update(pickListWithTeams: PickListWithTeams): Long

    @Update
    abstract fun update(pickList: PickList): Int

    @Transaction
    @Query("SELECT * FROM pickList WHERE pickListId = :pickListId")
    abstract fun getTeamsForPickList(pickListId: Long): Flow<List<PickListWithTeams>>

    fun followTeamsForPickList(pickListId: Long) =
        getTeamsForPickList(pickListId).distinctUntilChanged()
}