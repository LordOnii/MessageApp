package network

import model.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/send_message")
    fun sendMessage(@Body message: Message): Call<Void>

    @GET("/get_messages/{user_id}")
    fun getMessages(@Path("user_id") userId: String): Call<List<Message>>
}
