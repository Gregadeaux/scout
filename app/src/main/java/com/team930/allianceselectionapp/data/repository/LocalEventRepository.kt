package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventDao
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.EventWithTeams
import com.team930.allianceselectionapp.data.local.TeamEventCrossRef
import com.team930.allianceselectionapp.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalEventRepository @Inject constructor(
    private val eventDao: EventDao
) : EventRepository {
    override fun getAllEvents(): Flow<Resources<List<EventEntity>>> = eventDao.getAll().map { Resources.Success(it) }
    override fun getEvent(eventId: Int): Flow<Resources<EventEntity?>> = eventDao.getEventById(eventId).map { Resources.Success(it) }
    fun getEventWithTeams(eventId: Int): Flow<Resources<EventWithTeams>> = eventDao.getEventWithTeams(eventId).map { Resources.Success(it) }

    suspend fun saveEvents(events: List<EventEntity>) {
        eventDao.insertAll(events)
    }

    suspend fun saveTeamEventCrossRef(crossRefs: List<TeamEventCrossRef>) {
        crossRefs.forEach { crossRef ->
            eventDao.insertTeamEventCrossRef(crossRef)
        }
    }
}