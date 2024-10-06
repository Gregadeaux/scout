package com.team930.allianceselectionapp.data.repository

import com.team930.allianceselectionapp.data.local.GlobalState
import com.team930.allianceselectionapp.data.preferences.GlobalStatePreferences
import com.team930.allianceselectionapp.data.redux.GlobalStateAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalStateRepository @Inject constructor(
    private val globalStatePreferences: GlobalStatePreferences
) {
    val globalState: Flow<GlobalState> = globalStatePreferences.globalState

    suspend fun updateGlobalState(action: GlobalStateAction) {
        globalStatePreferences.updateGlobalState(action)
    }
}