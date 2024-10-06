package com.team930.allianceselectionapp.domain.usecases.event

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.repository.LocalEventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalEventsUseCase @Inject constructor(
    private val localEventRepository: LocalEventRepository
) {
    operator fun invoke(): Flow<Resources<List<EventEntity>>> {
        return localEventRepository.getAllEvents()
    }
}