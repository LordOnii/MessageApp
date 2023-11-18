package model

import androidx.compose.runtime.*
import network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.Constants

class MainViewModel {
    private val _messages: MutableState<List<Message>> = mutableStateOf(mutableListOf())
    val messages: MutableState<List<Message>> get() = _messages
    var currentConv: MutableState<Conversation> = mutableStateOf(Conversation("private", mutableListOf()))
    val allConv: MutableState<List<Conversation>> = mutableStateOf(mutableListOf())

    fun fetchMessages() {
        println("Start fetching messages...")

        // Call getMessages function here
        val obj = ApiClient.getMessages(Constants.userID)

        obj.enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    val newMessages: List<Message>? = response.body()
                    // Handle the list of messages
                    newMessages?.let { messages ->

                        for (message in messages) {
                            // if message is in the list already, don't add messages
                            if (!_messages.value.contains(message)) {
                                // boolean to know if the conv message is in already exists
                                val isConversationPresent = allConv.value.any { existingConversation ->
                                    existingConversation.members.size == message.conv.members.size && existingConversation.members.all { existingMember ->
                                        message.conv.members.any { it.userID == existingMember.userID }
                                    }
                                }
                                // new conv detected, add it to the list
                                if (!isConversationPresent) {
                                    allConv.value += message.conv
                                }

                                _messages.value += message
                            }
                        }
                    }
                    println("Messages fetched.")
                } else {
                    println("Error while fetching: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                println("Request failed: ${t.message}")
                throw t
            }
        })
    }

    fun sendMessages(message: String, conversation: Conversation) {
        // make sure to put us in the first place
        val convCopy = conversation.copy(
            members = conversation.members.map { it.copy() }.toMutableList()
        )
        convCopy.members.remove(ConversationMember(Constants.userID))
        println("----------------> $conversation")
        convCopy.members.add(0, ConversationMember(Constants.userID))

        val messagePacket = Message(message, convCopy)


        val obj = ApiClient.apiService.sendMessage(messagePacket)

        obj.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    println("Message sent successfully")

                    _messages.value += messagePacket
                } else {
                    // Handle error response
                    println("Failed to send message. Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
                println("Failed to send message. Error: ${t.message}")
            }
        })
    }

    fun getConvMessages(): MutableList<Message> {
        val list: MutableList<Message> = mutableListOf()
        for (message in _messages.value) {
            var addElement = true
            // compare members in message.conv and currentConv
            for (member in currentConv.value.members) {
                // if member in conv but not in message.conv, don't add it
                if (!message.conv.members.contains(member)) {
                    addElement = false
                }
            }
            // if there are more people in message.conv than in currentConv, don't add it
            if (addElement && message.conv.members.size == currentConv.value.members.size) {
                list += message
            }
        }
        return list
    }
}
