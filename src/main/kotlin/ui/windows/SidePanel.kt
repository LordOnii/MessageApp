package ui.windows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import model.MainViewModel
import ui.components.ConvBox
import ui.components.NewConvButton
import utils.Constants

@Composable
fun SidePanel(viewModel: MainViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .fillMaxWidth(0.15f)
    ) {
        // side panel is an info about your id and a list of conversation underneath
        // at the very top of the side panel there is a button for updating the messages

        // update button
        Button(
            onClick = { viewModel.fetchMessages() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(8.dp)
        ) {
            Text("Update")
        }

        // id display
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "Username : ${Constants.userID}", color = Color.Gray, fontSize = TextUnit(0.7f, TextUnitType.Em)
            )
        }


        // conversation list
        NewConvButton(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(8.dp),
            viewModel
        )
        // column of all conv
        LazyColumn(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            items(viewModel.allConv.value) { conv ->
                ConvBox(viewModel, conv)
            }
        }
    }
}