package com.team930.allianceselectionapp.domain.mapper

import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.presentation.model.Status
import com.team930.allianceselectionapp.presentation.model.Team

fun TeamEntity.toPresentationModel(): Team {
    val status = Status.entries.toTypedArray().random()

    return Team(
        teamNumber = teamNumber,
        status = status
    )
}