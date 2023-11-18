package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Conversation
import model.ConversationMember
import model.MainViewModel
import utils.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewConvButton(modifier: Modifier, viewModel: MainViewModel) {
    var typedMessageMember by remember { mutableStateOf("") }
    var isAlertVisible by remember { mutableStateOf(false) }
    val listMemberString = mutableListOf(Constants.userID)

    Button(
        onClick = { isAlertVisible = true }, modifier = modifier
    ) {
        Text("New")
    }

    if (isAlertVisible) {
        AlertDialog(
            title = { Text("Add new member") },
            onDismissRequest = {
                listMemberString.clear()
                typedMessageMember = ""
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        listMemberString.add(typedMessageMember)
                        typedMessageMember = ""
                    }) {
                        Text("Add")
                    }

                    Button(onClick = {
                        listMemberString.add(typedMessageMember)
                        val conv = Conversation(
                            if (listMemberString.size == 2) "private" else "group",
                            listMemberString.map { ConversationMember(it) }.toMutableList()
                        )
                        viewModel.allConv.value += conv
                        viewModel.currentConv.value = conv

                        isAlertVisible = false
                    }) {
                        Text("Finish")
                    }
                }
            },
            text = {
                TextField(
                    value = typedMessageMember,
                    onValueChange = { typedMessageMember = it },
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
            })
    }
}