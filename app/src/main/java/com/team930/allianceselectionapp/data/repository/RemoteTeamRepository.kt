package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.remote.Api
import com.team930.allianceselectionapp.data.remote.dto.TeamDTO
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.data.mapper.toTeamEntity
import com.team930.allianceselectionapp.domain.repository.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteTeamRepository @Inject constructor(
    private val api: Api
) : TeamRepository {
    override fun getAllTeams() = flow<Resources<List<TeamEntity>>> {
        emit(Resources.Loading())

        val teamList = try {
            api.getTeams()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        }

        teamList?.let {
            emit(Resources.Success(it.map(TeamDTO::toTeamEntity)))
            emit(Resources.Loading(false))
        }

    }.flowOn(Dispatchers.IO)

    override fun getTeam(teamNumber: String) = flow<Resources<TeamEntity?>> {
        emit(Resources.Error("Not implemented"))
    }.flowOn(Dispatchers.IO)
}