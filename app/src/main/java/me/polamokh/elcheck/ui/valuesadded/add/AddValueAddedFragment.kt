package me.polamokh.elcheck.ui.valuesadded.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                R.id.value_added_percetange -> viewModel.setValueAddedType(true)
                R.id.value_added_amount -> viewModel.setValueAddedType(false)
            }
        }
        binding.valueAddedType.check(R.id.value_added_percetange)

        binding.valueAddedValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) viewModel.setValueAddedValue(s.toString().toDouble())
                else viewModel.setValueAddedValue(0.0)
            }
        })

        binding.saveValueAdded.setOnClickListener {
            viewModel.saveValueAdded()
        }
    }
}