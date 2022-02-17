package oleg_pronin.nasa.data.net

import oleg_pronin.nasa.data.net.api.NasaApi
import oleg_pronin.nasa.data.net.retrofit.RetrofitClient
import oleg_pronin.nasa.domain.entity.APOD
import oleg_pronin.nasa.domain.repository.NasaRepo
import retrofit2.Callback

private const val BASE_URL = "https://api.nasa.gov/"

class NasaRepoImpl : NasaRepo {
    private val retrofit = RetrofitClient.getClient(BASE_URL)
    private var api: NasaApi = retrofit.create(NasaApi::class.java)

    override fun getPictureOfTheDay(apiKey: String, callback: Callback<APOD>) {
        api.getAstronomyPictureOfTheDay(apiKey).enqueue(callback)
    }
}