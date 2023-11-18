package model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import utils.Constants

@Serializable
data class ConversationMember(
    @SerializedName("id")
    val userID: String,
)

@Serializable
data class Conversation(
    @SerializedName("type")
    val type: String,
    @SerializedName("members")
    val members: MutableList<ConversationMember>,
)

@Serializable
data class Message(
    @SerializedName("conv")
    val conv: Conversation,
    @SerializedName("content")
    val content: String,
) {
    fun isFromClient(): Boolean {
        return conv.members[0] == ConversationMember(Constants.userID)
    }

    fun sender(): String {
        return if (conv.members[0].userID != Constants.userID) {
            conv.members[0].userID
        } else {
            conv.members[1].userID
        }
    }
}

fun Message(content: String, conversation: Conversation): Message {
    return Message(conversation, content)
}

