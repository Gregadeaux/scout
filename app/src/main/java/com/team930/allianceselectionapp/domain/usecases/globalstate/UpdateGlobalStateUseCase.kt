package com.team930.allianceselectionapp.domain.usecases.globalstate
import com.team930.allianceselectionapp.data.preferences.GlobalStatePreferences
import com.team930.allianceselectionapp.data.redux.GlobalStateAction
import javax.inject.Inject

class UpdateGlobalStateUseCase @Inject constructor(
    private val globalStatePreferences: GlobalStatePreferences
) {
    suspend operator fun invoke(action: GlobalStateAction) {
        globalStatePreferences.updateGlobalState(action)
    }
}