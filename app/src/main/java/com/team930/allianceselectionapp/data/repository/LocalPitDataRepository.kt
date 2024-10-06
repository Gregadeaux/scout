package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.PitDataDao
import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.local.PitDataWithTeamAndEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPitDataRepository @Inject constructor(
    private val pitDataDao: PitDataDao
) {
    fun getPitDataWithTeamAndEvent(id: Long): Flow<Resources<PitDataWithTeamAndEvent>> =
        pitDataDao.getPitDataWithTeamAndEvent(id).map { Resources.Success(it) }

    fun getPitDataByEvent(competitionId: Int): Flow<Resources<List<PitDataEntity>>> =
        pitDataDao.getPitDataByEvent(competitionId).map { Resources.Success(it) }

    suspend fun savePitData(pitData: PitDataEntity) {
        pitDataDao.insertPitData(pitData)
    }

    suspend fun savePitData(pitDataList: List<PitDataEntity>) {
        pitDataDao.insertAll(pitDataList)
    }

    suspend fun getPitDataByCompetitionAndTeam(competitionId: Int, teamNumber: Int): PitDataEntity? {
        return pitDataDao.getPitDataByCompetitionAndTeam(competitionId, teamNumber)
    }

    suspend fun updatePitData(pitData: PitDataEntity) {
        pitDataDao.updatePitData(pitData)
    }
}