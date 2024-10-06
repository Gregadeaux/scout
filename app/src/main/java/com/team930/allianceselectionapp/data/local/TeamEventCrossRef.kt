package com.team930.allianceselectionapp.data.local

import androidx.room.Entity

@Entity(primaryKeys = ["teamNumber", "competitionId"])
data class TeamEventCrossRef(
    val teamNumber: String,
    val competitionId: Int
)

//class TeamEventCrossRefBuilder {
//    private var teamNumber: String = ""
//    private var competitionId: Int = 0
//    private var driveTrain: String? = null
//    private var robotWidth: Double? = null
//    private var programmingLanguage: String? = null
//
//    fun teamNumber(teamNumber: String) = apply { this.teamNumber = teamNumber }
//    fun competitionId(competitionId: Int) = apply { this.competitionId = competitionId }
//    fun driveTrain(driveTrain: String?) = apply { this.driveTrain = driveTrain }
//    fun robotWidth(robotWidth: Double?) = apply { this.robotWidth = robotWidth }
//    fun programmingLanguage(programmingLanguage: String?) = apply { this.programmingLanguage = programmingLanguage }
//
//    fun build() = TeamEventCrossRef(
//        teamNumber = teamNumber,
//        competitionId = competitionId,
//        driveTrain = driveTrain,
//        robotWidth = robotWidth,
//        programmingLanguage = programmingLanguage
//    )
//}

