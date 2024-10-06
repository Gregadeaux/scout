package com.team930.allianceselectionapp.domain.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getAllEvents(): Flow<Resources<List<EventEntity>>>
    fun getEvent(eventId: Int): Flow<Resources<EventEntity?>>
}