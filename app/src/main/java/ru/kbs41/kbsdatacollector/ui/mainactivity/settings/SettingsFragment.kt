package ru.kbs41.kbsdatacollector.ui.mainactivity.settings

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Debug
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.transition.Visibility
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.databinding.SettingsFragmentBinding
import java.net.Socket

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

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Настройки"

        setActions()

        return binding.root

    }

    private fun setActions() {


        binding.fabConnectionCheck.setOnClickListener {
            GlobalScope.launch {
                //Debug.waitForDebugger()
                val client = Socket("192.168.1.52", 11000)
                client.outputStream.write("Hello from the client!<EOF>".toByteArray())
                client.close()
            }

        }

        binding.btnCheckPassword.setOnClickListener {
            if (binding.etPasswordForSettings.text.toString().equals("6831296")) {
                binding.etPasswordForSettings.setText("")
                binding.passwordLayout.visibility = View.GONE
                binding.settingsLayout.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Вы ввели неверный пароль", Toast.LENGTH_SHORT)
                    .show()
            }
        }

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
    }

}