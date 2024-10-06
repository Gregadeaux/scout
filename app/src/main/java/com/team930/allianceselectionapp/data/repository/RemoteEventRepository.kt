package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.data.mapper.PartialPitData
import com.team930.allianceselectionapp.data.mapper.toEventEntity
import com.team930.allianceselectionapp.data.mapper.toPartialPitData
import com.team930.allianceselectionapp.data.mapper.toTeamEntity
import com.team930.allianceselectionapp.data.remote.Api
import com.team930.allianceselectionapp.data.remote.dto.EventDTO
import com.team930.allianceselectionapp.domain.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteEventRepository @Inject constructor(
    private val api: Api
) : EventRepository {
    override fun getAllEvents() = flow<Resources<List<EventEntity>>> {
        emit(Resources.Loading())

        val eventList = try {
            api.getEvents()
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        }

        eventList?.let {
            emit(Resources.Success(it.map(EventDTO::toEventEntity)))
            emit(Resources.Loading(false))
        }

    }.flowOn(Dispatchers.IO)

    override fun getEvent(eventId: Int) = flow<Resources<EventEntity?>> {
        emit(Resources.Error("Not implemented"))
    }.flowOn(Dispatchers.IO)

    fun getTeamsByEventKey(eventKey: String, eventYear: String) = flow<Resources<Map<TeamEntity, PartialPitData>>> {
        emit(Resources.Loading())

        val teamList = try {
            api.getTeamsByEventKey(eventYear, eventKey)
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resources.Error("Could not load data"))
            null
        }

        teamList?.let {
            emit(Resources.Success(it.associate { t -> t.toTeamEntity() to t.toPartialPitData() }))
            emit(Resources.Loading(false))
        }

    }.flowOn(Dispatchers.IO)
}