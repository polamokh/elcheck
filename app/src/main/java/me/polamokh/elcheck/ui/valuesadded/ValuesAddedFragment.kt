package me.polamokh.elcheck.ui.valuesadded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentValuesAddedBinding
import me.polamokh.elcheck.ui.SharedViewModel
import me.polamokh.elcheck.ui.orders.details.OrderDetailsFragmentDirections

@AndroidEntryPoint
class ValuesAddedFragment : Fragment() {

    private lateinit var binding: FragmentValuesAddedBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

        sharedViewModel.valuesAdded.observe(viewLifecycleOwner) {
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