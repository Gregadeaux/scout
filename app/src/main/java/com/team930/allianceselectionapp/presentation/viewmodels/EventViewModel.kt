package com.team930.allianceselectionapp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team930.allianceselectionapp.data.repository.EventRepository
import com.team930.allianceselectionapp.data.local.EventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    val eventRepository: EventRepository
) : ViewModel() {
    private val _eventEntityData = mutableStateOf<List<EventEntity>>(listOf())
    val eventEntityData: State<List<EventEntity>> = _eventEntityData

    init {
        fetchLocalEvents()
    }

    fun fetchLocalEvents() {
        viewModelScope.launch {
            eventRepository.getAllEvents().onEach { eventEntityList: List<EventEntity> ->
                _eventEntityData.value = eventEntityList
            }.launchIn(this)
        }
    }
}