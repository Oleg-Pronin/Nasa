package oleg_pronin.nasa.domain.repository

import oleg_pronin.nasa.domain.entity.APOD

interface NasaRepo {
    fun getPictureOfTheDay(callback: Any)
}