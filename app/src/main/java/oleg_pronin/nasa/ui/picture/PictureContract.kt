package oleg_pronin.nasa.ui.picture

import androidx.lifecycle.LiveData
import oleg_pronin.nasa.State

class PictureContract {
    interface ViewModal {
        val pictureLiveData: LiveData<State>

        fun getPictureOfTheDay()
    }
}