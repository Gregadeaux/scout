package com.team930.allianceselectionapp.domain.usecases.event

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.data.repository.LocalEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTeamsByEventUseCase @Inject constructor(
    private val localEventRepository: LocalEventRepository
) {
    operator fun invoke(eventId: Int): Flow<Resources<List<TeamEntity>>> = flow {
        emit(Resources.Loading())
        localEventRepository.getEventWithTeams(eventId).map { resource ->
            when (resource) {
                is Resources.Success -> {
                    val eventWithTeams = resource.data
                    val teamsWithCrossRef = eventWithTeams?.teams ?: emptyList()
                    Resources.Success(teamsWithCrossRef)
                }
                is Resources.Error -> Resources.Error(resource.message ?: "An error occurred")
                is Resources.Loading -> Resources.Loading()
            }
        }.collect { emit(it) }
    }
}