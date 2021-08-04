package me.polamokh.elcheck.core.expenses.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.polamokh.elcheck.core.databinding.FragmentAddExpenseBinding

@AndroidEntryPoint
class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding

    private val viewModel: AddExpenseViewModel by viewModels()

    private lateinit var adapter: ExpenseParticipantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExpenseParticipantsAdapter { isChecked, expenseParticipant ->
            viewModel.recalculateParticipantExpenses(isChecked, expenseParticipant)
        }
        binding.expenseParticipantsRecyclerView.adapter = adapter

        viewModel.expenseParticipants.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.onNotifyItemChanged.observe(viewLifecycleOwner) { position ->
            if (position != -1) {
                adapter.notifyItemChanged(position)
                viewModel.onNotifyItemChangedComplete()
            }
        }

        binding.saveExpense.setOnClickListener {
            viewModel.saveExpense()
        }

        binding.expenseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) viewModel.setExpenseAmount(s.toString().toDouble())
                else viewModel.setExpenseAmount(0.0)
                viewModel.recalculateParticipantExpenses()
            }
        })
    }
}