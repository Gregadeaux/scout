package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.EventDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    fun getAllEvents(): Flow<List<EventEntity>> {
        return eventDao.getAll()
    }
}