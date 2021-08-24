package me.polamokh.elcheck.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.R
import me.polamokh.elcheck.data.local.expense.Expense
import me.polamokh.elcheck.databinding.FragmentExpensesBinding
import me.polamokh.elcheck.ui.BaseFragment

@AndroidEntryPoint
class ExpensesFragment : BaseFragment<FragmentExpensesBinding>() {

    private val viewModel: ExpensesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUI() {
        val expensesAdapter = ExpensesAdapter { expense ->
            viewModel.deleteExpense(expense)
        }

        binding.expensesRecyclerView.apply {
            setHasFixedSize(true)
            adapter = expensesAdapter
        }

        viewModel.expenses.observe(viewLifecycleOwner) {
            expensesAdapter.submitList(it)
            handleEmptyList(it)
        }
    }

    private fun handleEmptyList(it: List<Expense>) {
        binding.emptyLayout.emptyMsg.isVisible = it.isEmpty()
        if (it.isEmpty()) {
            binding.emptyLayout.emptyMsg.text = getString(
                R.string.format_empty_list_with_msg,
                getString(R.string.expenses).lowercase(),
                getString(R.string.expense).lowercase(),
                getString(R.string.participants).lowercase(),
                getString(R.string.expenses).lowercase()
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