package com.team930.allianceselectionapp.data.mapper

import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.remote.dto.TeamDTO
import com.team930.allianceselectionapp.data.local.TeamEntity

fun TeamDTO.toTeamEntity() = TeamEntity(
    teamNumber = teamNumber.toString()
)

data class PartialPitData(
    val teamNumber: String,
    val driveTrain: String?,
    val robotWidth: Double?,
    val programmingLanguage: String?
)

fun TeamDTO.toPartialPitData() = PartialPitData(
    teamNumber = teamNumber.toString(),
    driveTrain = driveTrain,
    robotWidth = robotWidth?.toDouble(),
    programmingLanguage = programmingLanguage
)