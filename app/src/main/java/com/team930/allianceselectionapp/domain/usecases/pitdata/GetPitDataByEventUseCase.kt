package com.team930.allianceselectionapp.domain.usecases.pitdata

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.repository.LocalPitDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPitDataByEventUseCase @Inject constructor(
    private val localPitDataRepository: LocalPitDataRepository
) {
    operator fun invoke(eventId: Int): Flow<Resources<List<PitDataEntity>>> = flow {
        emit(Resources.Loading())
        localPitDataRepository.getPitDataByEvent(eventId).map { resource ->
            when (resource) {
                is Resources.Success -> Resources.Success(resource.data ?: emptyList())
                is Resources.Error -> Resources.Error(resource.message ?: "An error occurred")
                is Resources.Loading -> Resources.Loading()
            }
        }.collect { emit(it) }
    }
}