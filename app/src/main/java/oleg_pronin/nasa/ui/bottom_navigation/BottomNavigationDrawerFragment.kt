package oleg_pronin.nasa.ui.bottom_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import oleg_pronin.nasa.R
import oleg_pronin.nasa.databinding.LayoutBottomNavigationBinding

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private var _binding: LayoutBottomNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBottomNavigationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_1 -> Toast.makeText(context, "Menu 1", Toast.LENGTH_SHORT).show()
                R.id.menu_2 -> Toast.makeText(context, "Menu 2", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}