package com.team930.allianceselectionapp.domain.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.TeamEntity
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getAllTeams(): Flow<Resources<List<TeamEntity>>>
    fun getTeam(teamNumber: String): Flow<Resources<TeamEntity?>>
}