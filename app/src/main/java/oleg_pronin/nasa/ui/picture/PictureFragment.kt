package oleg_pronin.nasa.ui.picture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import oleg_pronin.nasa.State
import oleg_pronin.nasa.databinding.FragmentPictureBinding
import oleg_pronin.nasa.utils.createSnackbarAndShow
import java.time.LocalDate

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
    }

    private fun initViewModel(viewModel: PictureContract.ViewModal) {
        viewModel.picture.observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(State: State) {
        when (State) {
            is State.Success -> {
//                val movie = State.data as Movie

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