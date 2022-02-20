package oleg_pronin.nasa.ui.setting

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import oleg_pronin.nasa.R
import oleg_pronin.nasa.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val items = mapOf(
        Configuration.UI_MODE_NIGHT_NO to "Светлая",
        Configuration.UI_MODE_NIGHT_YES to "Темная"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
        initUIEvent()
    }

    override fun onResume() {
        super.onResume()

        binding.listItem.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.text_item,
                items.values.toList()
            )
        )
    }

    private fun initValues() {
        binding.listItem.setText(
            items[resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK]
        )
    }

    private fun initUIEvent() {
        binding.listItem.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}