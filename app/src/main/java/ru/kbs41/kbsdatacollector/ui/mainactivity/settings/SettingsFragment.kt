package ru.kbs41.kbsdatacollector.ui.mainactivity.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Debug
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import ru.kbs41.kbsdatacollector.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val model: SettingsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SettingsFragmentBinding.inflate(inflater, container, false)

        model.fetchData(binding)
        
        binding.etRepresentation.doAfterTextChanged {
            binding.representation = it.toString()
        }
        binding.cbUseHttps.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.useHttps = isChecked
            model.updateSettings()
        }
        binding.etServer.doAfterTextChanged {
            binding.server = it.toString()
            model.updateSettings()
        }
        binding.etPort.doAfterTextChanged {
            binding.port = it.toString()
            model.updateSettings()
        }
        binding.etUser.doAfterTextChanged {
            binding.user = it.toString()
            model.updateSettings()
        }
        binding.etPassword.doAfterTextChanged {
            binding.password = it.toString()
            model.updateSettings()
        }

        return binding.root

    }

}