package oleg_pronin.nasa.ui.picture

import android.os.Bundle
import android.view.HapticFeedbackConstants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import oleg_pronin.nasa.R
import oleg_pronin.nasa.State
import oleg_pronin.nasa.databinding.FragmentPictureBinding
import oleg_pronin.nasa.domain.entity.APOD
import oleg_pronin.nasa.utils.createSnackbarAndShow

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureContract.ViewModal by viewModels<PictureViewModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUIEvent()
        initViewModel()
        setBottomSheetBehavior(binding.bottomSheetInclude.bottomSheetContainer)

        viewModel.getPictureOfTheDay()
    }

    private fun initUIEvent() {
        binding.imageApod.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initViewModel() {
        viewModel.pictureLiveData.observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(State: State) {
        when (State) {
            is State.Success -> {
                val apod = State.data as APOD

                apod.url?.let {
                    binding.let {
                        Glide
                            .with(this)
                            .load(apod.url)
                            .error(R.drawable.ic_load_error_photo)
                            .placeholder(R.drawable.ic_no_photo)
                            .into(it.imageApod)

                        it.bottomSheetInclude.bottomSheetDescriptionHeader.text = apod.title
                        it.bottomSheetInclude.bottomSheetDescription.text = apod.explanation
                    }
                }
            }
            is State.Loading -> {

            }
            is State.Error -> {
                State.error.message?.let {
                    view?.createSnackbarAndShow(it)
                }

                binding.let {
                    it.imageApod.setImageResource(R.drawable.ic_load_error_photo)
                    it.bottomSheetInclude.bottomSheetDescription.text = State.error.message ?: ""
                }
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    companion object {
        fun newInstance() = PictureFragment()
        private var isMain = true
    }
}