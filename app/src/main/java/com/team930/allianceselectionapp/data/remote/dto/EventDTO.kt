package com.team930.allianceselectionapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventDTO(
    @Json(name = "CompetitionId")
    val competitionId: Int,
    @Json(name = "Year")
    val year: Int,
    @Json(name = "ActiveSeason")
    val activeSeason: Boolean,
    @Json(name = "Name")
    val name: String,
    @Json(name = "EventKey")
    val eventKey: String? = null
)