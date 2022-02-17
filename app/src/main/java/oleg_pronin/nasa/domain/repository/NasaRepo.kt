package oleg_pronin.nasa.domain.repository

import oleg_pronin.nasa.domain.entity.APOD
import retrofit2.Callback

interface NasaRepo {
    fun getPictureOfTheDay(apiKey: String, callback: Callback<APOD>)
}