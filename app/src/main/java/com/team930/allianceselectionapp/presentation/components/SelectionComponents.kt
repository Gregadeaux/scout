package com.team930.allianceselectionapp.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team930.allianceselectionapp.core.util.Resources
import com.team930.allianceselectionapp.presentation.theme.AllianceSelectionTheme
import com.team930.allianceselectionapp.presentation.viewmodels.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllianceSelectionPanel(teamsViewModel: TeamViewModel = hiltViewModel()) {
    val loadingState by teamsViewModel.loading.collectAsState()
    val isLoading = loadingState is Resources.Loading

    val teamsState by teamsViewModel.teams.collectAsState()
    println(teamsState)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text("Alliance Selection")
                    },
                    actions = {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp).offset(x = (-12).dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            IconButton(onClick = { teamsViewModel.updateFromServer() }) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    },
                )
            }
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AllianceRow(allianceNumber = "1", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "2", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "3", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "4", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "5", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "6", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "7", teamNumbers = listOf("123", "1234", "12345"))
                AllianceRow(allianceNumber = "8", teamNumbers = listOf("123", "1234", "12345"))
            }
        }
        Surface(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.Red)) { Text("Column 2")}
        Surface(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MaterialTheme.colorScheme.primary)) { Text("Column 3")}
    }
}

@Composable
fun TeamButton(teamNumber: String, modifier: Modifier = Modifier) {
    ElevatedButton(
        onClick = { println(teamNumber) },
        modifier = modifier.fillMaxWidth()
    ) {
        Text(teamNumber)
    }
}

@Composable
fun AllianceRow(allianceNumber: String, teamNumbers: List<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .width(425.dp)
    ) {
        Text(
            text = allianceNumber,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(start = 7.dp)
                .width(12.dp)
        )
        Spacer(Modifier.size(16.dp))
        teamNumbers.map {
            TeamButton(it, modifier = Modifier
                .padding(horizontal = 3.dp)
                .weight(1f))
        }
    }
}

@Composable
fun UnselectedTeamsGrid(unselectedTeams: List<String>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(horizontal = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        items(unselectedTeams) { team ->
            TeamButton(teamNumber = team)
        }
    }
}

@Preview
@Composable
fun TeamButtonPreview() {
    TeamButton("111")
}

@Preview
@Composable
fun AllianceRowPreview() {
    AllianceRow(allianceNumber = "1", teamNumbers = listOf("123", "1234", "12345"))
}

@Preview
@Composable
fun UnselectedTeamsGridPreview() {
    UnselectedTeamsGrid(unselectedTeams = listOf("111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11","111", "1234", "12345", "11",))
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 1280, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AllianceSelectionPanelPreview() {
    AllianceSelectionTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
//            AllianceSelectionPanel()
        }
    }
}