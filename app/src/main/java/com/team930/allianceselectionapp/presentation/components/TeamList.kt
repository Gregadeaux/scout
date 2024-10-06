package com.team930.allianceselectionapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team930.allianceselectionapp.presentation.model.PitDataStatus
import com.team930.allianceselectionapp.presentation.model.Status
import com.team930.allianceselectionapp.presentation.model.Team
import com.team930.allianceselectionapp.presentation.model.TeamPitData
import com.team930.allianceselectionapp.presentation.theme.AllianceSelectionTheme

@Composable
fun TeamList(teams: List<TeamPitData>, modifier: Modifier = Modifier, onTeamSelected: (TeamPitData) -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(teams) { team ->
            Column {
                TeamStatusItem(team, onTeamSelected)
                HorizontalDivider(thickness = 1.dp, color = Color.White)
            }
        }
    }
}

@Composable
fun TeamStatusItem(team: TeamPitData, onTeamSelected: (TeamPitData) -> Unit) {
    val backgroundColor = when (team.pitDataStatus) {
        PitDataStatus.ASSIGNED ->  MaterialTheme.colorScheme.inversePrimary//Color(red=0x4B, green = 0xD9, blue = 0x4B, alpha = 0x77)
        PitDataStatus.INCOMPLETE -> MaterialTheme.colorScheme.errorContainer // Color(red=0xE8, green = 0x53, blue = 0x4D, alpha = 0x77)
        PitDataStatus.COMPLETE -> MaterialTheme.colorScheme.primaryContainer//Color(red=0x4B, green = 0xD9, blue = 0xD9, alpha = 0x77)
        PitDataStatus.NONE -> MaterialTheme.colorScheme.surfaceVariant
    }
    
    val textColor = when (team.pitDataStatus) {
        PitDataStatus.ASSIGNED -> MaterialTheme.colorScheme.onPrimaryContainer//Color(red=0x4B, green = 0xD9, blue = 0x4B)
        PitDataStatus.INCOMPLETE -> MaterialTheme.colorScheme.onErrorContainer//Color(red=0xE8, green = 0x53, blue = 0x4D)
        PitDataStatus.COMPLETE -> MaterialTheme.colorScheme.onPrimaryContainer//Color(red=0x4B, green = 0xD9, blue = 0xD9)
        PitDataStatus.NONE -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val statusText = when (team.pitDataStatus) {
        PitDataStatus.COMPLETE -> "Complete"
        PitDataStatus.INCOMPLETE -> "Incomplete"
        PitDataStatus.ASSIGNED -> "Assigned"
        PitDataStatus.NONE -> ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
            .clickable { onTeamSelected(team) }
    ) {
        Text(
            text = "Team ${team.teamNumber}",
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        if (statusText.isNotEmpty()) {
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }
    }
}

//@Preview
//@Composable
//fun TeamStatusScreen() {
//    val teams = listOf(
//        Team("1", Status.COMPLETE),
//        Team("2", Status.INCOMPLETE),
//        Team("3", Status.ASSIGNED),
//        Team("4", Status.NONE)
//    )
//
//    AllianceSelectionTheme {
//        TeamList(teams = teams, onTeamSelected = { })
//    }
//}