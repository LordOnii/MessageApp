package network

import model.Message
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utils.Constants

object ApiClient {

    private val BASE_URL = Constants.SERVER_ADDRESS

    private val retrofit by lazy {
        try {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e: Exception) {
            println("Retrofit initialization error: $e")
            throw e
        }
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getMessages(userID: String): Call<List<Message>> {
        return apiService.getMessages(userID)
    }
}