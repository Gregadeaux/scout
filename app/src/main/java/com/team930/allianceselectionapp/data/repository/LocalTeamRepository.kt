package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.TeamDao
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.data.local.TeamEventCrossRef
import com.team930.allianceselectionapp.data.local.TeamWithEvents
import com.team930.allianceselectionapp.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalTeamRepository @Inject constructor(
    private val teamDao: TeamDao
) : TeamRepository {
    override fun getAllTeams(): Flow<Resources<List<TeamEntity>>> = teamDao.getAll().map { Resources.Success(it) }
    override fun getTeam(teamNumber: String): Flow<Resources<TeamEntity?>> = teamDao.getTeamByNumber(teamNumber).map { Resources.Success(it) }
    fun getTeamWithEvents(teamNumber: String): Flow<Resources<TeamWithEvents>> = teamDao.getTeamWithEvents(teamNumber).map { Resources.Success(it) }

    suspend fun saveTeams(teams: List<TeamEntity>) {
        teamDao.insertAll(teams)
    }

    suspend fun saveTeamEventCrossRef(crossRefs: List<TeamEventCrossRef>) {
        crossRefs.forEach { crossRef ->
            teamDao.insertTeamEventCrossRef(crossRef)
        }
    }
}