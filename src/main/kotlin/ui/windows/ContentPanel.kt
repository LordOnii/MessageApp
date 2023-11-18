package ui.windows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import model.MainViewModel
import model.Message
import ui.components.MessageText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentPanel(viewModel: MainViewModel) {
    val displayMessages: MutableState<MutableList<Message>> = mutableStateOf(viewModel.getConvMessages())
    // row with messages (most recent at the bottom)
    // and a way to send message
    Column {
        // messages display
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.88f)
        ) {
            // updates itself each time viewModel.messages.value changes
            items(displayMessages.value) { message ->
                MessageText(message)
            }
        }


        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black
        )


        // message text field + send button
        Row {
            var typedMessage by remember { mutableStateOf("") }
            // message field
            TextField(
                value = typedMessage,
                onValueChange = {
                    typedMessage = it
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight()
                    .padding(8.dp)
                    .heightIn(min = 40.dp)
                    // handle keyboard
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Enter) {
                            // remove the \n at the end before sending the message
                            viewModel.sendMessages(
                                typedMessage.substring(0, typedMessage.length - 1),
                                viewModel.currentConv.value
                            )
                            return@onKeyEvent true
                        }
                        false
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            // send button
            Button(
                onClick = {
                    viewModel.sendMessages(typedMessage, viewModel.currentConv.value)
                    typedMessage = ""
                    println(viewModel.messages)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {}
        }
    }
}
