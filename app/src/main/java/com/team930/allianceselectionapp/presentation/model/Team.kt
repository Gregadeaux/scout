package com.team930.allianceselectionapp.presentation.model

enum class Status {
    COMPLETE,
    INCOMPLETE,
    ASSIGNED,
    NONE
}

data class Team(
    val teamNumber: String,
    val status: Status
)
