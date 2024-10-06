package com.team930.allianceselectionapp.data.source.local

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.team930.allianceselectionapp.data.local.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Entity
data class Alliance(
    @PrimaryKey val allianceId: Long,
    val allianceNumber: Number,
    val allianceEventId: Long
)

data class EventAlliances(
    @Embedded val eventEntity: EventEntity,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "allianceEventId"
    )
    val alliances: List<Alliance>
)

@Dao
abstract class AllianceDao{
    @Transaction
    @Query("SELECT * FROM alliance WHERE allianceEventId = :eventId")
    abstract fun getEventAlliances(eventId: Long): Flow<EventAlliances>

    fun followEventAlliances(eventId: Long) = getEventAlliances(eventId).distinctUntilChanged()
}