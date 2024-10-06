package com.team930.allianceselectionapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StringListModal(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select an Item") },
        text = {
            LazyColumn {
                items(items.size) { index ->
                    Text(
                        text = items[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(items[index])
                                onDismissRequest()
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}

@Composable
fun TextFieldModal(
    title: String,
    initialText: String = "",
    onTextChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val textState = remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = {
            TextField(
                value = textState.value,
                keyboardOptions = keyboardOptions,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onTextChange(textState.value)
                onConfirm()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    )
}