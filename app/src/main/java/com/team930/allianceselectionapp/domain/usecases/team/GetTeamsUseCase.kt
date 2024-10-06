package com.team930.allianceselectionapp.domain.usecases.team

import com.team930.allianceselectionapp.core.util.Local
import com.team930.allianceselectionapp.core.util.Resources
import kotlinx.coroutines.flow.Flow
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.domain.repository.TeamRepository
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(
    @Local private val repository: TeamRepository
) {
    operator fun invoke(): Flow<Resources<List<TeamEntity>>> = repository.getAllTeams()
}