package oleg_pronin.nasa.data.net.api

import oleg_pronin.nasa.domain.entity.APOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    fun getAstronomyPictureOfTheDay(@Query("api_key") apiKey: String): Call<APOD>
}