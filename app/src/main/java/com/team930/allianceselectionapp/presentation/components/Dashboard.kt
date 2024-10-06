package com.team930.allianceselectionapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.GlobalState
import com.team930.allianceselectionapp.presentation.viewmodels.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun DashboardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .clip(RoundedCornerShape(25.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp)),
        color = MaterialTheme.colorScheme.primaryContainer,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    navController: NavController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val globalState by dashboardViewModel.globalState.collectAsState()
    val events by dashboardViewModel.events.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier=modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = "Dashboard")
                },
                actions = {
                    val eventName = globalState.selectedEvent?.let { "${it.name} (${it.year})" } ?: "Select Event"
                    TextButton(onClick = { dashboardViewModel.refreshEventsAndTeams() }) {
                        Text(text = "Update")
                    }
                    TextButton(onClick = { scope.launch { sheetState.show() }.invokeOnCompletion {
                        if (sheetState.isVisible) {
                            showBottomSheet = true
                        }
                    } }) {
                        Text(text = eventName)
                    }
                }
            )
        }
    ) { innerPadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                LazyColumn {
                    items(events) { event ->
                        ListItem(
                            headlineContent = {
                                Text("${event.name} (${event.year})")
                          },
                            modifier = Modifier.clickable {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                    dashboardViewModel.selectEvent(event)
                                }
                            }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                DashboardButton(
                    text = "Alliance Selection",
                    onClick = { navController.navigate("alliance_selection") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                DashboardButton(
                    text = "Match Preview",
                    onClick = { navController.navigate("match_preview") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                DashboardButton(
                    text = "Data Tables",
                    onClick = { navController.navigate("data_tables") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                DashboardButton(
                    text = "Pick List Editor",
                    onClick = { navController.navigate("pick_list_editor") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                DashboardButton(
                    text = "Pit Scouting",
                    onClick = { navController.navigate("pit_scouting") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                DashboardButton(
                    text = "Match Scouting",
                    onClick = { navController.navigate("match_scouting") },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }
    }
}