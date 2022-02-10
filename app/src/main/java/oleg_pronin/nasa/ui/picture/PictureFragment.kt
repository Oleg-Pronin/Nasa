package oleg_pronin.nasa.ui.picture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import oleg_pronin.nasa.R
import oleg_pronin.nasa.State
import oleg_pronin.nasa.databinding.FragmentPictureBinding
import oleg_pronin.nasa.domain.entity.APOD
import oleg_pronin.nasa.utils.createSnackbarAndShow

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureContract.ViewModal by viewModels<PictureViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel(viewModel)

        viewModel.getPictureOfTheDay()
    }

    private fun initViewModel(viewModel: PictureContract.ViewModal) {
        viewModel.pictureLiveData.observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(State: State) {
        when (State) {
            is State.Success -> {
                val apod = State.data as APOD

                apod.url?.let {
                    Glide
                        .with(this)
                        .load(apod.url)
                        .error(R.drawable.ic_load_error_photo)
                        .placeholder(R.drawable.ic_no_photo)
                        .into(binding.imageApod)
                }
            }
            is State.Loading -> {

            }
            is State.Error -> {
                State.error.message?.let { view?.createSnackbarAndShow(it) }
            }
        }
    }
    
    companion object {
        fun newInstance() = PictureFragment()
        private var isMain = true
    }
}