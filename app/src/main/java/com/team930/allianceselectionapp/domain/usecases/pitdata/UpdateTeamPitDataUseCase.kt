package com.team930.allianceselectionapp.domain.usecases.pitdata

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.repository.LocalPitDataRepository
import com.team930.allianceselectionapp.domain.mapper.toPitDataEntity
import com.team930.allianceselectionapp.presentation.model.TeamPitData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTeamPitDataUseCase @Inject constructor(
    private val repository: LocalPitDataRepository
) {
    operator fun invoke(team: TeamPitData): Flow<Resources<PitDataEntity>> = flow {
        try {
            emit(Resources.Loading())
            val pitDataEntity = repository.getPitDataByCompetitionAndTeam(team.competitionId, team.teamNumber.toInt())
            if (pitDataEntity != null) {
                val updatedPitDataEntity = team.toPitDataEntity(pitDataEntity)
                repository.updatePitData(updatedPitDataEntity)
                emit(Resources.Success(updatedPitDataEntity))
            } else {
                emit(Resources.Error("PitDataEntity not found"))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}