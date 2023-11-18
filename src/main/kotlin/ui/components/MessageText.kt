package ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Message

@Composable
fun MessageText(message: Message) {
    val alignment = if (message.isFromClient()) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }

    val msg: String = if (message.isFromClient()) {
        message.content
    }
     else {
         "${message.sender()} : ${message.content}"
    }

Box(
modifier = Modifier
.fillMaxWidth()
.padding(10.dp),
contentAlignment = alignment
) {
    Text(msg, fontSize = 20.sp)
}
}


