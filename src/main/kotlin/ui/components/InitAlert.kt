package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.MainViewModel
import ui.windows.MainScreen
import utils.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InitAlert() {
    var typedMessageUsername by remember { mutableStateOf("") }
    var typedMessageServer by remember { mutableStateOf("") }
    var isUsernameAlertVisible by remember { mutableStateOf(true) }
    var isMainScreenVisible by remember { mutableStateOf(false) }
    var isServerAlertVisible by remember { mutableStateOf(false) }

    if (isUsernameAlertVisible) {
        AlertDialog(onDismissRequest = {}, title = { Text("Select your username") }, confirmButton = {
            Button(onClick = {
                Constants.userID = typedMessageUsername
                isServerAlertVisible = true
                isUsernameAlertVisible = false
            }) {
                Text("Confirm")
            }
        }, text = {
            TextField(
                value = typedMessageUsername,
                onValueChange = { typedMessageUsername = it },
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        })
    }

    if (isServerAlertVisible) {
        AlertDialog(onDismissRequest = {},
            title = { Text("Enter the server address [IP_ADDRESS:PORT]") },
            confirmButton = {
                Button(onClick = {
                    Constants.SERVER_ADDRESS = "http://$typedMessageServer"
                    isMainScreenVisible = true
                    isServerAlertVisible = false
                }) {
                    Text("Confirm")
                }
            },
            text = {
                TextField(
                    value = typedMessageServer,
                    onValueChange = { typedMessageServer = it },
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
            })
    }

    if (isMainScreenVisible) {
        MainScreen(viewModel = MainViewModel())
    }
}