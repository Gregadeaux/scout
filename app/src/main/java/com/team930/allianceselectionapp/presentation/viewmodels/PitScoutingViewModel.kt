package com.team930.allianceselectionapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.repository.GlobalStateRepository
import com.team930.allianceselectionapp.domain.usecases.pitdata.GetPitDataByEventUseCase
import com.team930.allianceselectionapp.domain.mapper.toTeamPitData
import com.team930.allianceselectionapp.domain.usecases.pitdata.UpdateTeamPitDataUseCase
import com.team930.allianceselectionapp.presentation.model.TeamPitData
import com.team930.allianceselectionapp.presentation.toUnitResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PitScoutingViewModel @Inject constructor(
    private val globalStateRepository: GlobalStateRepository,
    private val getPitDataByEventUseCase: GetPitDataByEventUseCase,
    private val updateTeamPitDataUseCase: UpdateTeamPitDataUseCase
) : ViewModel() {

    private val _teams = MutableStateFlow<List<TeamPitData>>(emptyList())
    val teams: StateFlow<List<TeamPitData>> get() = _teams

    private val _selectedTeam = MutableStateFlow<TeamPitData?>(null)
    val selectedTeam: StateFlow<TeamPitData?> get() = _selectedTeam

    private val _loading = MutableStateFlow<Resources<Unit>>(Resources.Success(Unit))
    val loading: StateFlow<Resources<Unit>> = _loading

    init {
        observeSelectedEvent()
    }

    fun selectTeam(team: TeamPitData) {
        _selectedTeam.value = team
    }

    fun updateTeam(team: TeamPitData) {
        viewModelScope.launch {
            updateTeamPitDataUseCase(team).collect { resource ->
                _loading.value = resource.toUnitResource()
                if (resource is Resources.Success) {
                    val updatedTeam = resource.data?.toTeamPitData()
                    if (updatedTeam != null) {
                        _teams.value = _teams.value.map {
                            if (it.teamNumber == updatedTeam.teamNumber) updatedTeam else it
                        }

                        if (_selectedTeam.value?.teamNumber == updatedTeam.teamNumber) {
                            _selectedTeam.value = updatedTeam
                        }
                    }
                }
            }
        }
    }

    private fun observeSelectedEvent() {
        viewModelScope.launch {
            globalStateRepository.globalState.map { it.selectedEvent }
                .onEach { event ->
                    event?.let { fetchTeamsForEvent(it.competitionId) }
                }
                .launchIn(this)
        }
    }

    private fun fetchTeamsForEvent(eventId: Int) {
        viewModelScope.launch {
            getPitDataByEventUseCase(eventId).collect { resource ->
                _loading.value = resource.toUnitResource()
                if (resource is Resources.Success) {
                    val teamsWithCrossRef = resource.data ?: emptyList()
                    val teamPitData = teamsWithCrossRef.map { it.toTeamPitData() }
                    _teams.value = teamPitData
                }
            }
        }
    }

}