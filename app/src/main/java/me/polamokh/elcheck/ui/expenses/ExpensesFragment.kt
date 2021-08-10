package me.polamokh.elcheck.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentExpensesBinding
import me.polamokh.elcheck.ui.SharedViewModel
import me.polamokh.elcheck.ui.orders.details.OrderDetailsFragmentDirections

@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private lateinit var binding: FragmentExpensesBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val viewModel: ExpensesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expensesAdapter = ExpensesAdapter { expense ->
            viewModel.deleteExpense(expense)
            true
        }
        binding.expensesRecyclerView.apply {
            adapter = expensesAdapter
        }

        sharedViewModel.expenses.observe(viewLifecycleOwner) {
            expensesAdapter.submitList(it)
        }

        binding.addExpense.setOnClickListener {
            findNavController().navigate(
                OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddExpenseFragment(
                    requireArguments().getLong("orderId")
                )
            )
        }
    }

    companion object {
        fun newInstance(orderId: Long) =
            ExpensesFragment().apply {
                arguments = Bundle().apply {
                    putLong("orderId", orderId)
                }
            }
    }
}