package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Conversation
import model.ConversationMember
import model.MainViewModel
import utils.Constants

@Composable
fun ConvBox(viewModel: MainViewModel, conversation : Conversation) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(4.dp)
            .padding(start = 8.dp)
            .clickable { viewModel.currentConv.value = conversation
            println(conversation)}
    ) {
        val otherUser = mutableListOf<ConversationMember>()
        for (member in conversation.members) {
            if (member.userID != Constants.userID) {
                otherUser += member
            }
        }
        Text(otherUser[0].userID)
    }
}