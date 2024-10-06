package com.team930.allianceselectionapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.GlobalState
import com.team930.allianceselectionapp.data.redux.GlobalStateAction
import com.team930.allianceselectionapp.data.repository.GlobalStateRepository
import com.team930.allianceselectionapp.domain.usecases.event.FetchAndSaveEventTeamsUseCase
import com.team930.allianceselectionapp.domain.usecases.event.FetchAndSaveEventsUseCase
import com.team930.allianceselectionapp.domain.usecases.event.GetLocalEventsUseCase
import com.team930.allianceselectionapp.domain.usecases.globalstate.UpdateGlobalStateUseCase
import com.team930.allianceselectionapp.presentation.toUnitResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getLocalEventsUseCase: GetLocalEventsUseCase,
    private val updateGlobalStateUseCase: UpdateGlobalStateUseCase,
    private val fetchAndSaveEventsUseCase: FetchAndSaveEventsUseCase,
    private val fetchAndSaveEventTeamsUseCase: FetchAndSaveEventTeamsUseCase,
    private val globalStateRepository: GlobalStateRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventEntity>>(emptyList())
    val events: StateFlow<List<EventEntity>> get() = _events

    private val _globalState = MutableStateFlow(GlobalState())
    val globalState: StateFlow<GlobalState> get() = _globalState

    private var _loading = MutableStateFlow<Resources<Unit>>(Resources.Success(Unit))
    val loading: StateFlow<Resources<Unit>> = _loading.asStateFlow()

    init {
        loadLocalEvents()
        loadGlobalState()
    }

    private fun loadLocalEvents() {
        viewModelScope.launch {
            getLocalEventsUseCase().collect { resource ->
                if (resource is Resources.Success) {
                    _events.value = resource.data ?: emptyList()
                }
            }
        }
    }

    private fun loadGlobalState() { // Add this function
        viewModelScope.launch {
            globalStateRepository.globalState.collect { state ->
                _globalState.value = state
            }
        }
    }

    fun selectEvent(event: EventEntity) {
        viewModelScope.launch {
            updateGlobalStateUseCase(GlobalStateAction.SetSelectedEvent(event))
        }
    }

    fun fetchAndSaveEvents() {
        viewModelScope.launch {
            fetchAndSaveEventsUseCase().collect { resource ->
                _loading.value = resource.toUnitResource()
                if (resource is Resources.Success) {
                    _events.value = resource.data ?: emptyList()
                }
            }
        }
    }

    fun refreshEventsAndTeams() {
        viewModelScope.launch {
            _loading.value = Resources.Loading()
            fetchAndSaveEventsUseCase().collect { eventResource ->
                _loading.value = eventResource.toUnitResource()
                if (eventResource is Resources.Success) {
                    _events.value = eventResource.data ?: emptyList()
                    _events.value.forEach { event ->
                        fetchAndSaveEventTeamsUseCase(event).collect { teamResource ->
                            if (teamResource is Resources.Error) {
                                _loading.value = teamResource.toUnitResource()
                                return@collect
                            }
                        }
                    }
                    _loading.value = Resources.Success(Unit)
                }
            }
        }
    }
}