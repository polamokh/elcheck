package me.polamokh.elcheck.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.databinding.FragmentExpensesBinding

@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private lateinit var binding: FragmentExpensesBinding

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
        }
        binding.expensesRecyclerView.apply {
            adapter = expensesAdapter
        }

        viewModel.expenses.observe(viewLifecycleOwner) {
            expensesAdapter.submitList(it)
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