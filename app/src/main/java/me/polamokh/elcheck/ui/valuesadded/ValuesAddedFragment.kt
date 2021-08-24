package me.polamokh.elcheck.ui.valuesadded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.databinding.FragmentValuesAddedBinding
import me.polamokh.elcheck.ui.BaseFragment

@AndroidEntryPoint
class ValuesAddedFragment : BaseFragment<FragmentValuesAddedBinding>() {

    private val viewModel: ValuesAddedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentValuesAddedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        val valuesAddedAdapter = ValuesAddedAdapter { viewModel.deleteValueAdded(it) }

        binding.valuesAddedRecyclerView.apply {
            setHasFixedSize(true)
            adapter = valuesAddedAdapter
        }

        viewModel.valuesAdded.observe(viewLifecycleOwner) {
            valuesAddedAdapter.submitList(it)
            binding.emptyLayout.emptyMsg.isVisible = it.isEmpty()
            if (it.isEmpty()) {
                binding.emptyLayout.emptyMsg.text = getString(
                    R.string.format_empty_list,
                    getString(R.string.values_added).lowercase(),
                    getString(R.string.value_added).lowercase()
                )
            }
        }
    }

    companion object {
        fun newInstance(orderId: Long) =
            ValuesAddedFragment().apply {
                arguments = Bundle().apply {
                    putLong("orderId", orderId)
                }
            }
    }
}