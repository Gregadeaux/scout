package com.team930.allianceselectionapp.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team930.allianceselectionapp.presentation.theme.AllianceSelectionTheme

val redBackground = Color(red=0xE8, green = 0x53, blue = 0x4D)
val greenBackground = Color(red=0x4B, green = 0xD9, blue = 0x4B)

@Composable
fun IconButton(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(25.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

@Composable
fun CommonPopupButton(
    buttonText: String,
    subtitleText: String,
    showPopup: Boolean,
    onButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modalContent: @Composable () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(25.dp))
                .background(backgroundColor)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp))
                .clickable(onClick = onButtonClick),
            contentAlignment = Alignment.Center
        ) {
            Text(text = buttonText, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitleText,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        if (showPopup) {
            modalContent()
        }
    }
}

@Composable
fun TextFieldPopupButton(
    buttonText: String,
    subtitleText: String,
    initialText: String = "",
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isGreenBackground: Boolean = false
) {
    var showPopup by remember { mutableStateOf(false) }

    CommonPopupButton(
        buttonText = buttonText,
        subtitleText = subtitleText,
        showPopup = showPopup,
        onButtonClick = { showPopup = true },
        onDismissRequest = { showPopup = false },
        modalContent = {
            TextFieldModal(
                title = "Enter Text",
                initialText = initialText,
                keyboardOptions = keyboardOptions,
                onTextChange = { newText ->
                    onTextChange(newText)
                    showPopup = false
                },
                onConfirm = { showPopup = false },
                onDismissRequest = { showPopup = false }
            )
        },
        modifier = modifier,
        backgroundColor = if (isGreenBackground) greenBackground else MaterialTheme.colorScheme.surfaceVariant
    )
}

@Composable
fun DropdownPopupButton(
    buttonText: String,
    subtitleText: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isGreenBackground: Boolean = false
) {
    var showPopup by remember { mutableStateOf(false) }

    CommonPopupButton(
        buttonText = buttonText,
        subtitleText = subtitleText,
        showPopup = showPopup,
        onButtonClick = { showPopup = true },
        onDismissRequest = { showPopup = false },
        modalContent = {
            StringListModal(
                items = options,
                onItemSelected = { selectedItem ->
                    onOptionSelected(selectedItem)
                    showPopup = false
                },
                onDismissRequest = { showPopup = false }
            )
        },
        modifier = modifier,
        backgroundColor = if (isGreenBackground) greenBackground else MaterialTheme.colorScheme.surfaceVariant
    )
}

@Composable
fun SplitSquareButton(
    label: String,
    positiveText: String,
    negativeText: String,
    selected: Boolean?,
    onSelectedChange: (Boolean?) -> Unit,
    modifier: Modifier = Modifier
) {
    val positiveBackgroundColor by animateColorAsState(
        targetValue = if (selected == true) greenBackground else Color.Transparent
    )
    val negativeBackgroundColor by animateColorAsState(
        targetValue = if (selected == false) redBackground else Color.Transparent
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)// Adjust the size as needed
                .aspectRatio(1f)
                .clip(RoundedCornerShape(25.dp)) // Clip the box to a rounded rectangle
                .background(MaterialTheme.colorScheme.secondaryContainer) // Set the background color of the box
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(25.dp))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Button(
                    onClick = {
                        onSelectedChange(if (selected == true) null else true)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(positiveBackgroundColor),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    AutoSizeText(positiveText, color = MaterialTheme.colorScheme.onSurface)
                }
                VerticalDivider(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Button(
                    onClick = {
                        onSelectedChange(if (selected == false) null else false)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(negativeBackgroundColor),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RectangleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    AutoSizeText(negativeText, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AutoSizeText(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
        )
    }
}

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    minFontSize: TextUnit = 12.sp,
    maxFontSize: TextUnit = 100.sp,
    color: Color = Color.White
) {
    var fontSize by remember { mutableStateOf(maxFontSize) }
    Text(
        text = text,
        style = style.copy(fontSize = 12.sp),
        color = color,
        softWrap = false,
        maxLines = 1,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                fontSize = if (fontSize > minFontSize) (fontSize.value - 1).sp else minFontSize
            }
        }
    )
}

@Preview
@Composable
fun ExampleScreen() {
    var selected by remember { mutableStateOf<Boolean?>(null) }

    SplitSquareButton(
        label = "Choose an option",
        positiveText = "Y",
        negativeText = "N",
        selected = selected,
        onSelectedChange = { selected = it }
    )
}

@Preview
@Composable
fun GridPreview() {
    val questions = listOf(
        "Question 1",
        "Question 2",
        "Question 3",
        "Question 4",
        "Question 5",
        "Question 6",
        "Question 7",
        "Question 8",
        "Question 9"
    )

    val selectedStates = remember { mutableStateOf(List(questions.size) { null as Boolean? }) }

    AllianceSelectionTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            items(questions.size) { index ->
                SplitSquareButton(
                    label = questions[index],
                    positiveText = "Y",
                    negativeText = "N",
                    selected = selectedStates.value[index],
                    onSelectedChange = { newState ->
                        selectedStates.value = selectedStates.value.toMutableList().apply {
                            this[index] = newState
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ExampleIconButtonScreen() {
    IconButton(
        icon = {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Check Icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        },
        onClick = { /* Handle click */ }
    )
}