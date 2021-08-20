package me.polamokh.elcheck.ui.valuesadded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentValuesAddedBinding

@AndroidEntryPoint
class ValuesAddedFragment : Fragment() {

    private lateinit var binding: FragmentValuesAddedBinding

    private val viewModel: ValuesAddedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentValuesAddedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val valuesAddedAdapter = ValuesAddedAdapter { viewModel.deleteValueAdded(it) }
        binding.valuesAddedRecyclerView.adapter = valuesAddedAdapter

        viewModel.valuesAdded.observe(viewLifecycleOwner) {
            valuesAddedAdapter.submitList(it)
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