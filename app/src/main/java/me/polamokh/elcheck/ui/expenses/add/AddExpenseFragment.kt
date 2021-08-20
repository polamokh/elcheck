package me.polamokh.elcheck.ui.expenses.add

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
import me.polamokh.elcheck.databinding.FragmentAddExpenseBinding
import me.polamokh.elcheck.model.ExpenseParticipant

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

        binding.saveExpense.isEnabled = isSaveExpenseEnabled(null, null)

        viewModel.onNotifyItemChanged.observe(viewLifecycleOwner) { position ->
            if (position != -1) {
                adapter.notifyItemChanged(position)
                viewModel.onNotifyItemChangedComplete()
            }

            binding.saveExpense.isEnabled =
                isSaveExpenseEnabled(
                    viewModel.expenseParticipants.value,
                    binding.expenseAmount.editText?.text
                )
        }

        binding.saveExpense.setOnClickListener {
            viewModel.saveExpense(binding.expenseName.editText?.text.toString())
            findNavController().navigateUp()
        }

        binding.expenseAmount.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty() && s.toString().toDoubleOrNull() != null)
                    viewModel.setExpenseAmount(s.toString().toDouble())
                else viewModel.setExpenseAmount(0.0)
                binding.saveExpense.isEnabled =
                    isSaveExpenseEnabled(viewModel.expenseParticipants.value, s)
            }
        })
    }

    private fun isSaveExpenseEnabled(
        participants: List<*>?,
        expenseAmount: CharSequence?
    ): Boolean {
        if (participants == null)
            return false
        if (expenseAmount == null)
            return false
        return participants.any { participant ->
            (participant as ExpenseParticipant).isChecked
        } && !expenseAmount.isNullOrEmpty()
    }
}