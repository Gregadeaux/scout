package com.team930.allianceselectionapp.domain.usecases.event

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.local.TeamEventCrossRef
import com.team930.allianceselectionapp.data.repository.LocalPitDataRepository
import com.team930.allianceselectionapp.data.repository.LocalTeamRepository
import com.team930.allianceselectionapp.data.repository.RemoteEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAndSaveEventTeamsUseCase @Inject constructor(
    private val remoteEventRepository: RemoteEventRepository,
    private val localTeamRepository: LocalTeamRepository,
    private val localPitDataRepository: LocalPitDataRepository
) {
    operator fun invoke(event: EventEntity): Flow<Resources<Unit>> = flow {
        emit(Resources.Loading())

        remoteEventRepository.getTeamsByEventKey(event.eventKey.toString(), event.year.toString()).collect { resource ->
            when (resource) {
                is Resources.Success -> {
                    val teamsMap = resource.data
                    val teams = teamsMap?.keys?.toList()
                    if (teams != null) {
                        localTeamRepository.saveTeams(teams)

                        val crossRefs = teams.map { team ->
                            TeamEventCrossRef(
                                teamNumber = team.teamNumber,
                                competitionId = event.competitionId
                            )
                        }

                        localTeamRepository.saveTeamEventCrossRef(crossRefs)

                        val pitDataList = teamsMap.entries.map { entry ->
                            val team = entry.key
                            val pitData = entry.value
                            pitData.let {
                                PitDataEntity(
                                    id = 0,
                                    teamNumber = team.teamNumber,
                                    competitionId = event.competitionId,
                                    driveTrain = it.driveTrain,
                                    robotWidth = it.robotWidth,
                                    programmingLanguage = it.programmingLanguage
                                )
                            }
                        }

                        localPitDataRepository.savePitData(pitDataList)

                        emit(Resources.Success(Unit))
                    } else {
                        emit(Resources.Error("No teams found"))
                    }
                }
                is Resources.Error -> {
                    emit(Resources.Error(resource.message ?: "An error occurred"))
                }
                is Resources.Loading -> {
                    emit(Resources.Loading())
                }
            }
        }
    }
}