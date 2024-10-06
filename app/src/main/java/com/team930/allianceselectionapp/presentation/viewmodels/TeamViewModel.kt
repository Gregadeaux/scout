package com.team930.allianceselectionapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.domain.usecases.remote.GetRemoteTeamsUseCase
import com.team930.allianceselectionapp.domain.usecases.team.GetTeamUseCase
import com.team930.allianceselectionapp.domain.usecases.team.GetTeamsUseCase
import com.team930.allianceselectionapp.domain.mapper.toPresentationModel
import com.team930.allianceselectionapp.presentation.model.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val getRemoteTeamsUseCase: GetRemoteTeamsUseCase,
    private val getTeamsUseCase: GetTeamsUseCase,
    private val getTeamUseCase: GetTeamUseCase,
) : ViewModel() {

    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> get() = _teams

    private val _selectedTeam = MutableStateFlow<Team?>(null)
    val selectedTeam: StateFlow<Team?> get() = _selectedTeam

    private var _loading = MutableStateFlow<Resources<Unit>>(Resources.Success(Unit))
    val loading: StateFlow<Resources<Unit>> = _loading.asStateFlow()

    fun updateFromServer() {
        viewModelScope.launch{
            getRemoteTeamsUseCase().map { it.data ?: emptyList() }
                .map { it.map { it.toPresentationModel() } }
                .onEach { _teams.value = it }
                .launchIn(this)
        }
    }

    fun loadTeams() {
        viewModelScope.launch {
            getTeamsUseCase().map { it.data ?: emptyList() }
                .map { it.map { it.toPresentationModel() } }
                .onEach { _teams.value = it }
                .launchIn(this)
        }
    }

    fun selectTeam(teamNumber: String) {
        viewModelScope.launch {
            getTeamUseCase(teamNumber).map { it.data?.toPresentationModel() }
                .onEach { _selectedTeam.value = it }
                .launchIn(this)
        }
    }
}