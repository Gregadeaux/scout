package com.team930.allianceselectionapp.domain.usecases.event

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.repository.LocalEventRepository
import com.team930.allianceselectionapp.data.repository.RemoteEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAndSaveEventsUseCase @Inject constructor(
    private val remoteEventRepository: RemoteEventRepository,
    private val localEventRepository: LocalEventRepository
) {
    operator fun invoke(): Flow<Resources<List<EventEntity>>> = flow {
        emit(Resources.Loading())
        remoteEventRepository.getAllEvents().collect { resource ->
            when (resource) {
                is Resources.Success -> {
                    resource.data?.let { events ->
                        localEventRepository.saveEvents(events)
                        emit(Resources.Success(events))
                    } ?: emit(Resources.Success(emptyList()))
                }
                is Resources.Error -> {
                    emit(Resources.Error(resource.message ?: "Unknown error"))
                }
                is Resources.Loading -> {
                    emit(Resources.Loading())
                }
            }
        }
    }
}