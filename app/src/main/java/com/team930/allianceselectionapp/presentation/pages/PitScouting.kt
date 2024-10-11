package com.team930.allianceselectionapp.presentation.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team930.allianceselectionapp.presentation.components.DropdownPopupButton
import com.team930.allianceselectionapp.presentation.components.SplitSquareButton
import com.team930.allianceselectionapp.presentation.components.TeamList
import com.team930.allianceselectionapp.presentation.components.TextFieldPopupButton
import com.team930.allianceselectionapp.presentation.model.DrivetrainOptions
import com.team930.allianceselectionapp.presentation.model.PitDataStatus
import com.team930.allianceselectionapp.presentation.viewmodels.PitScoutingViewModel

data class Question(
    val name: String,
    var value: Boolean? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PitScoutingScreen(viewModel: PitScoutingViewModel = hiltViewModel()) {
    val selectedTeam by viewModel.selectedTeam.collectAsState()
    val statusOrder = listOf(PitDataStatus.ASSIGNED, PitDataStatus.INCOMPLETE, PitDataStatus.NONE, PitDataStatus.COMPLETE)
    val teams by viewModel.teams.collectAsState()
    val sortedTeams = teams.sortedWith(compareBy { statusOrder.indexOf(it.pitDataStatus) })

    var questions by remember {
        mutableStateOf(
            listOf(
                Question("Can Score In Amp"),
                Question("Can Score In Speaker"),
                Question("Can Score In Trap"),
                Question("Can Climb"),
                Question("Question 5"),
                Question("Question 6"),
                Question("Question 7"),
                Question("Question 8"),
                Question("Question 9")
            )
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = "Pit Scouting")
                }
            )
        }
    ) { innerPadding ->

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            // Master layout: TeamList
            TeamList(
                teams = sortedTeams,
                onTeamSelected = { viewModel.selectTeam(it) },
                modifier = Modifier.weight(1f)
            )

            // Detail layout: 3x3 grid of PitScoutingButtons
            if (selectedTeam != null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        SplitSquareButton(
                            label = questions[0].name,
                            positiveText = "Y",
                            negativeText = "N",
                            selected = questions[0].value,
                            onSelectedChange = { newState ->
                                questions = questions.toMutableList().apply {
                                    this[0] = this[0].copy(value = newState)
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                        DropdownPopupButton(
                            subtitleText = "Drivetrain",
                            buttonText = selectedTeam?.driveTrain ?: "Select",
                            options = DrivetrainOptions.entries.map(DrivetrainOptions::toString),
                            isGreenBackground = DrivetrainOptions.fromString(selectedTeam?.driveTrain ?: "") != DrivetrainOptions.OTHER,
                            onOptionSelected = { selectedItem ->
                                selectedTeam?.let {
                                    viewModel.updateTeam(
                                        it.copy(driveTrain = selectedItem)
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                        TextFieldPopupButton(
                            buttonText = selectedTeam?.robotWidth?.toString() ?: "Enter Width",
                            subtitleText = "Robot Width",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isGreenBackground = selectedTeam?.robotWidth?.let { it > 0 } ?: false,
                            onTextChange = { newWidth ->
                                selectedTeam?.let {
                                    viewModel.updateTeam(
                                        it.copy(robotWidth = newWidth.toDouble())
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {

                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(3f)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Select a team to view details",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
//
//@Preview(widthDp = 1280, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PitScoutingPreview() {
//    val statuses = listOf(Status.COMPLETE, Status.INCOMPLETE, Status.ASSIGNED, Status.NONE)
//    val teams = List(20) { index ->
//        TeamStatus(
//            teamNumber = index + 1,
//            status = statuses[Random.nextInt(statuses.size)]
//        )
//    }
//
//    PitScoutingScreen(teams = teams)
//}