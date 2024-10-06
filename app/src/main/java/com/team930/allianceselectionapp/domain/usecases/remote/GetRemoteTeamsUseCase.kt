package com.team930.allianceselectionapp.domain.usecases.remote

import com.team930.allianceselectionapp.core.util.Remote
import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteTeamsUseCase @Inject constructor(
    @Remote private val repository: TeamRepository
) {
    operator fun invoke(): Flow<Resources<List<TeamEntity>>> = repository.getAllTeams()
}