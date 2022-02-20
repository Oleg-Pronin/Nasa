package oleg_pronin.nasa.ui.picture

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import oleg_pronin.nasa.R
import oleg_pronin.nasa.State
import oleg_pronin.nasa.databinding.FragmentPictureBinding
import oleg_pronin.nasa.domain.entity.APOD
import oleg_pronin.nasa.ui.bottom_navigation.BottomNavigationDrawerFragment
import oleg_pronin.nasa.ui.search.SearchFragment
import oleg_pronin.nasa.ui.setting.SettingFragment
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_bottom_navigation, menu)
    }

    private fun initUIEvent() {
        binding.let { it ->
            it.imageApod.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            it.bottomAppBar.setNavigationOnClickListener {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }

            it.bottomAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.app_bar_search -> {
                        activity
                            ?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.container, SearchFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                        true
                    }
                    R.id.app_bar_setting -> {
                        activity
                            ?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.container, SettingFragment())
                            ?.addToBackStack(null)
                            ?.commit()
                        true
                    }
                    else -> false
                }
            }

            it.fab.setOnClickListener {
                viewModel.getPictureOfTheDay()
            }
        }


    }

    private fun initViewModel() {
        viewModel.pictureLiveData.observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(State: State) {
        when (State) {
            is State.Success -> {
                (State.data as APOD).let { apod ->
                    binding.let {
                        apod.url?.let { url ->
                            Glide
                                .with(this)
                                .load(url)
                                .error(R.drawable.ic_load_error_photo)
                                .placeholder(R.drawable.ic_no_photo)
                                .into(it.imageApod)
                        }

                        it.bottomSheetInclude.bottomSheetDescriptionHeader.text = apod.title
                        it.bottomSheetInclude.bottomSheetDescription.text = apod.explanation
                    }
                }
            }
            is State.Loading -> {
                binding.let {
                    it.imageApod.setImageResource(R.drawable.ic_no_photo)
                    it.bottomSheetInclude.bottomSheetDescriptionHeader.text = ""
                    it.bottomSheetInclude.bottomSheetDescription.text = ""
                }
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