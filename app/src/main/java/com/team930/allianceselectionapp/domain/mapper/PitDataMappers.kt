package com.team930.allianceselectionapp.domain.mapper

import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.presentation.model.TeamPitData

fun PitDataEntity.toTeamPitData() = TeamPitData(
    competitionId = competitionId,
    teamNumber = teamNumber,
    driveTrain = driveTrain,
    robotWidth = robotWidth,
    programmingLanguage = programmingLanguage
)

fun TeamPitData.toPitDataEntity(pitDataHolder: PitDataEntity) = pitDataHolder.copy(
    teamNumber = teamNumber,
    driveTrain = driveTrain,
    robotWidth = robotWidth,
    programmingLanguage = programmingLanguage
)