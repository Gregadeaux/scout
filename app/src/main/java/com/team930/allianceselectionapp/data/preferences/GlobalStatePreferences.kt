package com.team930.allianceselectionapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.team930.allianceselectionapp.data.local.EventDao
import com.team930.allianceselectionapp.data.local.GlobalState
import com.team930.allianceselectionapp.data.redux.GlobalStateAction
import com.team930.allianceselectionapp.data.redux.globalStateReducer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException


private val Context.dataStore by preferencesDataStore(name = "global_state")

class GlobalStatePreferences(context: Context, private val eventDao: EventDao) {

    private val dataStore = context.dataStore

    companion object {
        val SELECTED_EVENT_KEY = intPreferencesKey("selected_event")
    }

    val globalState: Flow<GlobalState> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedEventId = preferences[SELECTED_EVENT_KEY]
            val selectedEvent = selectedEventId?.let { id ->
                runBlocking { eventDao.getEventById(id).first() }
            }
            GlobalState(selectedEvent)
        }

    suspend fun updateGlobalState(action: GlobalStateAction) {
        println("Updating global state with action: $action")
        val currentState = globalState.first()
        println("Current state: $currentState")
        val newState = globalStateReducer(currentState, action)
        println("New state: $newState")
        newState.selectedEvent?.competitionId?.let {
            println("Saving selected event id: $it")
            dataStore.edit { preferences ->
                preferences[SELECTED_EVENT_KEY] = it
            }
        } ?: run {
            println("Removing selected event id")
            dataStore.edit { preferences ->
                preferences.remove(SELECTED_EVENT_KEY)
            }
        }
    }
}