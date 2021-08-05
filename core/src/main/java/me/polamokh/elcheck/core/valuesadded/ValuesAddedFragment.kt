package me.polamokh.elcheck.core.valuesadded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentValuesAddedBinding
import me.polamokh.elcheck.core.orders.details.OrderDetailsFragmentDirections

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

        val valuesAddedAdapter = ValuesAddedAdapter {
            viewModel.deleteValueAdded(it)
            true
        }
        binding.valuesAddedRecyclerView.adapter = valuesAddedAdapter

        viewModel.valuesAdded.observe(viewLifecycleOwner) {
            valuesAddedAdapter.submitList(it)
        }

        binding.addValueAdded.setOnClickListener {
            findNavController().navigate(
                OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddValueAddedFragment(
                    requireArguments().getLong("orderId")
                )
            )
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