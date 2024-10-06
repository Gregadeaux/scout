package com.team930.allianceselectionapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDTO(
    @Json(name = "PitDataID")
    val pitDataId: Int,
    @Json(name = "CompetitionID")
    val competitionID: Int,
    @Json(name = "TeamNumber")
    val teamNumber: Int,
    @Json(name = "DriveTrain")
    val driveTrain: String? = null,
    @Json(name = "RobotWidth")
    val robotWidth: Float? = null,
    @Json(name = "ProgrammingLanguage")
    val programmingLanguage: String? = null,
)