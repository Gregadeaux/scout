package com.team930.allianceselectionapp.data.remote

import com.team930.allianceselectionapp.data.remote.dto.EventDTO
import com.team930.allianceselectionapp.data.remote.dto.TeamDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/event/2024/2024ilch/teams")
    suspend fun getTeams(): List<TeamDTO>

    @GET("/event")
    suspend fun getEvents(): List<EventDTO>

    @GET("/event/{eventYear}/{eventKey}/teams")
    suspend fun getTeamsByEventKey(
        @Path("eventYear") eventYear: String,
        @Path("eventKey") eventKey: String
    ): List<TeamDTO>

}