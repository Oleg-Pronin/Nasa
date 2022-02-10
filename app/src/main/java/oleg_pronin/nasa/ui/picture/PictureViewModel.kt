package oleg_pronin.nasa.ui.picture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import oleg_pronin.nasa.BuildConfig
import oleg_pronin.nasa.State
import oleg_pronin.nasa.data.net.NasaRepoImpl
import oleg_pronin.nasa.domain.entity.APOD
import oleg_pronin.nasa.domain.repository.NasaRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PictureViewModel : ViewModel(), PictureContract.ViewModal {
    override val pictureLiveData = MutableLiveData<State>()
    private val nasaRepo: NasaRepo by lazy { NasaRepoImpl() }

    override fun getPictureOfTheDay() {
        pictureLiveData.postValue(State.Loading)

        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            State.Error(Throwable("You need API key"))
        } else {
            nasaRepo.getPictureOfTheDay(apiKey, object : Callback<APOD> {
                override fun onResponse(call: Call<APOD>, response: Response<APOD>) {
                    val serverResponse: APOD? = response.body()

                    pictureLiveData.postValue(
                        if (response.isSuccessful && serverResponse != null) {
                            State.Success(serverResponse)
                        } else {
                            State.Error(Throwable("Error server response"))
                        }
                    )
                }

                override fun onFailure(call: Call<APOD>, t: Throwable) {
                    pictureLiveData.postValue(State.Error(t))
                }
            })
        }


    }

}