package com.team930.allianceselectionapp.data.redux

import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.GlobalState

sealed class GlobalStateAction {
    data class SetSelectedEvent(val event: EventEntity) : GlobalStateAction()
    object ClearSelectedEvent : GlobalStateAction()
}

fun globalStateReducer(state: GlobalState, action: GlobalStateAction): GlobalState {
    return when (action) {
        is GlobalStateAction.SetSelectedEvent -> state.copy(selectedEvent = action.event)
        is GlobalStateAction.ClearSelectedEvent -> state.copy(selectedEvent = null)
    }
}