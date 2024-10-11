package com.team930.allianceselectionapp.presentation.model

import java.util.Locale

enum class PitDataStatus {
    COMPLETE,
    INCOMPLETE,
    ASSIGNED,
    NONE
}

enum class DrivetrainOptions {
    MECANUM,
    OMNI,
    TANK,
    SWERVE,
    OTHER;

    companion object {
        fun fromString(value: String): DrivetrainOptions {
            return when (value.lowercase(Locale.getDefault())) {
                "mecanum" -> MECANUM
                "omni" -> OMNI
                "tank" -> TANK
                "swerve" -> SWERVE
                else -> OTHER
            }
        }

        fun toString(value: DrivetrainOptions): String {
            return when (value) {
                MECANUM -> "Mecanum"
                OMNI -> "Omni"
                TANK -> "Tank"
                SWERVE -> "Swerve"
                OTHER -> "Other"
            }
        }
    }
}

data class TeamPitData(
    val teamNumber: String,
    val competitionId: Int,
    val driveTrain: String? = null,
    val robotWidth: Double? = null,
    val programmingLanguage: String? = null
) {
    val pitDataStatus: PitDataStatus
        get() {
            val optionalFields = listOf(driveTrain, robotWidth, programmingLanguage)
            val filledFieldsCount = optionalFields.count { it != null }

            return when (filledFieldsCount) {
                0 -> PitDataStatus.NONE
                optionalFields.size -> PitDataStatus.COMPLETE
                else -> PitDataStatus.INCOMPLETE
            }
        }
}