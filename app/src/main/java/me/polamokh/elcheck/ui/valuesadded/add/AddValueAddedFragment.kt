package me.polamokh.elcheck.ui.valuesadded.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentAddValueAddedBinding

@AndroidEntryPoint
class AddValueAddedFragment : Fragment() {

    private lateinit var binding: FragmentAddValueAddedBinding

    private val viewModel: AddValueAddedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddValueAddedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.valueAddedType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.value_added_percentage -> {
                    viewModel.setValueAddedType(true)
                    binding.valueAddedValue.setStartIconDrawable(R.drawable.ic_percentage_value_added)
                }
                R.id.value_added_amount -> {
                    viewModel.setValueAddedType(false)
                    binding.valueAddedValue.setStartIconDrawable(R.drawable.ic_amount_value_added)
                }
            }
        }

        binding.valueAddedType.check(R.id.value_added_percentage)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveValueAdded.isEnabled =
            isSaveValueAddedEnabled(binding.valueAddedValue.editText?.text)

        binding.valueAddedValue.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && s.toString().toDoubleOrNull() != null)
                    viewModel.setValueAddedValue(s.toString().toDouble())
                else viewModel.setValueAddedValue(0.0)
                binding.saveValueAdded.isEnabled = isSaveValueAddedEnabled(s)
            }
        })

        binding.saveValueAdded.setOnClickListener {
            viewModel.saveValueAdded()
            findNavController().navigateUp()
        }
    }

    private fun isSaveValueAddedEnabled(valueAddedValue: CharSequence?): Boolean {
        return !valueAddedValue.isNullOrEmpty()
    }
}