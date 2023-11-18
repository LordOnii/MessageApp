package ui.windows

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.MainViewModel
import androidx.compose.material.Divider as Divider1



@Preview
@Composable
fun MainScreen(viewModel : MainViewModel) {
    // fetch messages on init
    LaunchedEffect(true) {
        viewModel.fetchMessages()
    }

    Row {
        SidePanel(viewModel)
        Divider1(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()  // vertical
                .width(1.dp)
        )
        ContentPanel(viewModel)

    }
}