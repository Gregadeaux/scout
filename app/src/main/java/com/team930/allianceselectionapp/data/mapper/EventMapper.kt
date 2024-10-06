package com.team930.allianceselectionapp.data.mapper

import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.remote.dto.EventDTO

fun EventDTO.toEventEntity(): EventEntity {
    return EventEntity(
        competitionId = this.competitionId,
        year = this.year,
        activeSeason = this.activeSeason,
        name = this.name,
        eventKey = this.eventKey
    )
}