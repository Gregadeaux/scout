package com.team930.allianceselectionapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.team930.allianceselectionapp.data.local.GlobalState
import com.team930.allianceselectionapp.presentation.components.Dashboard
import com.team930.allianceselectionapp.presentation.pages.PitScoutingScreen
import com.team930.allianceselectionapp.presentation.viewmodels.DashboardViewModel
import kotlin.random.Random

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Dashboard.route,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Dashboard.route) {
            Dashboard(modifier, navController)
        }
        composable(NavigationItem.AllianceSelection.route) {
        }
        composable(NavigationItem.MatchPreview.route) {

        }
        composable(NavigationItem.DataTables.route) {

        }
        composable(NavigationItem.PickListEditor.route) {

        }
        composable(NavigationItem.PitScouting.route) {
            PitScoutingScreen()
        }
        composable(NavigationItem.MatchScouting.route) {

        }
        composable(NavigationItem.Settings.route) {

        }
    }
}